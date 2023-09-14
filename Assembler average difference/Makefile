all: run clean
	
run: build
	@./mean

build: mean.asm
	@nasm -f elf64 mean.asm -o mean.o
	@gcc -no-pie -fno-pie -o mean mean.o
	
clean:
	@rm -f mean.o
	@rm -f mean