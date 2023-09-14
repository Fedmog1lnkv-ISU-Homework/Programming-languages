%macro pushd 0
    push rax
    push rbx
    push rcx
    push rdx
    push rbp
%endmacro

%macro popd 0
    pop rax
    pop rbx
    pop rcx
    pop rdx
    pop rbp
%endmacro
section .text
	global main
	extern printf

main: 
    fild dword [number]
    fsqrt
    fistp dword [result]

print:
    pushd
    mov rdi, format
    mov rsi, [result]
    call printf
    popd

end:
    mov rax, 60
    xor rdi, rdi
    syscall

section .data
	number dd 144
	format db "Square root: %d", 0xA

section .bss
    result resb 1