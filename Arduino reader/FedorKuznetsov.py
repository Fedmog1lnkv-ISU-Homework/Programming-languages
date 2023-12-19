import pytz
import zmq
from datetime import datetime, timezone
import time
import sqlite3
import logging
import re

logging.basicConfig(filename='../FedorKuznetsov_client.log', level=logging.DEBUG,
                    format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger()
logger.info("Start App")

context = zmq.Context()

client = context.socket(zmq.SUB)
client.connect("tcp://127.0.0.1:5555")
client.subscribe('')

conn = sqlite3.connect('../FedorKuznetsov_server_log.db')
cursor = conn.cursor()

cursor.execute('''
    CREATE TABLE IF NOT EXISTS log (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        timestamp DATETIME,
        system_utc INTEGER,
        data TEXT
    )
''')
conn.commit()


def log_event(data):
    if re.search(r'[ptmPTM]\s*\d*', data):
        timestamp_utc0 = datetime.now(timezone.utc)
        system_utc_offset = -time.timezone
        cursor.execute("INSERT INTO log (timestamp, system_utc, data) VALUES (?, ?, ?)",
                       (timestamp_utc0, system_utc_offset, data))
        conn.commit()


timeout_seconds = 5
timeout_start = time.time()

while True:
    try:
        message = client.recv_string(zmq.NOBLOCK)
        log_event(message)
        timeout_start = time.time()
    except zmq.error.Again as e:
        time.sleep(0.5)
    except zmq.error.ZMQError as e:
        logger.critical(e)

    if time.time() - timeout_start > timeout_seconds:
        ind_log = False
        while True:
            try:
                test = client.recv_string(zmq.NOBLOCK)
                if test != None:
                    logger.info("Successfully getting data")
                    log_event(test)
                    timeout_start = time.time()
                    ind_log = False
                    break
                else:
                    if ind_log == False:
                        logger.error(f"Error receiving data")
                        ind_log = True
            except:
                if ind_log == False:
                    logger.error(f"Error receiving data")
                    ind_log = True
                time.sleep(0.3)

    else:
        time.sleep(1)
