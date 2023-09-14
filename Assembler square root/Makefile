all: run clean
	
run: build
	@./sqrt

build: sqrt.asm
	@nasm -f elf64 sqrt.asm -o sqrt.o
	@gcc -no-pie -fno-pie -o sqrt sqrt.o
	
clean:
	@rm -f sqrt.o
	@rm -f sqrt