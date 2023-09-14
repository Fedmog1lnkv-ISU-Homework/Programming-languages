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
    jge calculate
    
    mov esi, [x + edx * 4]
    cmp esi, [y + edx * 4]
    
    jl false
    true:
        sub esi, [y + edx * 4]
        jmp iteration

    false:
        mov esi, [y + edx * 4]
        sub esi, [x + edx * 4]

    iteration:
		add eax, esi
		inc edx
		jmp _loop

calculate:
    mov ecx, edx
    mov edx, 0
    div ecx

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