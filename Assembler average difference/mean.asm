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
    mov edx, 0
    mov eax, 0

_loop:
    cmp edx, [len]
    jge calculate_negative
    
    mov esi, [x + edx * 4]
    sub esi, [y + edx * 4]

    iteration:
		add eax, esi
		inc edx
		jmp _loop

calculate_negative:
    cmp eax, 0
    jge calculate
    neg eax
    mov ecx, [len]
    mov edx, 0
    div ecx
    neg eax
    jmp print

calculate: 
    mov ecx, [len]
    mov edx, 0
    div ecx
    jmp print

print:
    pushd
    mov rdi, format
    mov [result], eax
    mov rsi, [result]
    call printf
    popd

end:
    mov rax, 60
    xor rdi, rdi
    syscall

section .data
    x dd 5, 3, 2, 6, 1, 7, 4
    len dd ($ - x) / 4
    y dd 0, 10, 1, 9, 2, 8, 5
    format db "Average: %d (reminder: %d)", 0xA

section .bss
    result resb 1
