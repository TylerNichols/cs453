    .file  "main.java"
__SREG__ = 0x3f
__SP_H__ = 0x3e
__SP_L__ = 0x3d
__tmp_reg__ = 0
__zero_reg__ = 1
    .global __do_copy_data
    .global __do_clear_bss
    .text
.global main
    .type   main, @function
main:
    push r29
    push r28
    in r28,__SP_L__
    in r29,__SP_H__
/* prologue: function */
    call _Z18MeggyJrSimpleSetupv 
    /* Need to call this so that the meggy library gets set up */

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,2
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

MJ_L0:
    # Push true (1) onto stack
    ldi r24, 1
    push r24

    # if not(condition)
    # load a one byte expression off stack
    pop    r24
    ldi    r25,0
    cp     r24, r25
    # WANT breq MJ_L2
    brne   MJ_L1
    jmp    MJ_L2

    # while loop body
MJ_L1:
    # if statement

    # Button Literal Meggy.Button.A
    ldi    r24, 2
    push   r24

    # MeggyCheckButton
    call    _Z16CheckButtonsDownv
    lds     r24, Button_A
    push    r24

    pop	r24
    tst	r24
    breq	MJ_L6
MJ_L7:
    ldi	r24, 1
    jmp	MJ_L8
MJ_L6:
MJ_L8:
    # push one byte expression onto stack
    push	r24

    # load condition and branch if false
    # load one byte expression off stack
    pop	r24
    # load zero into reg
    ldi	r25, 0

    # use cp to set SREG
    cp	r24, r25
    brne	MJ_L4
    jmp	MJ_L3

    # then label for if
MJ_L4:
    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,6
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,0
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,0
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    jmp	MJ_L5

    # else label for if
MJ_L3:
    # if statement

    # Button Literal Meggy.Button.B
    ldi    r24, 1
    push   r24

    # MeggyCheckButton
    call    _Z16CheckButtonsDownv
    lds     r24, Button_B
    push    r24

    pop	r24
    tst	r24
    breq	MJ_L12
MJ_L13:
    ldi	r24, 1
    jmp	MJ_L14
MJ_L12:
MJ_L14:
    # push one byte expression onto stack
    push	r24

    # load condition and branch if false
    # load one byte expression off stack
    pop	r24
    # load zero into reg
    ldi	r25, 0

    # use cp to set SREG
    cp	r24, r25
    brne	MJ_L10
    jmp	MJ_L9

    # then label for if
MJ_L10:
    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,4
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,0
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,0
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    jmp	MJ_L11

    # else label for if
MJ_L9:
    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,2
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    # Push integer 7 onto stack
    ldi	r24,lo8(7)
    ldi	r25,hi8(7)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,0
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push integer 0 onto stack
    ldi	r24,lo8(0)
    ldi	r25,hi8(0)
    push	r25
    push	r24

    # Cast int to byte
    pop	r24
    pop	r25
    push	r24

    # Push color onto stack
    ldi	r22,0
    push	r22

    # Call Meggy.setPixel(x,y,color)
    pop	r20
    pop	r22
    pop	r24
    call	_Z6DrawPxhhh
    call	_Z12DisplaySlatev


    # done label for if
MJ_L11:


    # done label for if
MJ_L5:

    # Push integer 100 onto stack
    ldi	r24,lo8(100)
    ldi	r25,hi8(100)
    push	r25
    push	r24

    # Call Meggy.delay()
    pop    r24
    pop    r25
    call   _Z8delay_msj

    # jump to while test
    jmp    MJ_L0

    # end of while
MJ_L2:

/* epilogue start */
    endLabel:
    jmp endLabel
    ret
    .size   main, .-main
