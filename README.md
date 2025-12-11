# CS240FinalProject

All instructions are implemented in my code, to run and use them in MIPS download the custom language maker used for the assignment, drag the MarioGalaxyLanguage.java file to MARSLE->mars->mips->instructions->customLangs, and use the command prompt to add the language to MARS as seen in the tutorial. Then use the language switcher to be able to use my code or any custom code using my commands. I have also turned in part 1 of the project so you can see which commands are available and how to use them.

Instructions:

Name: OneUp
Function: Add
Binary: 000000 sssss ttttt fffff 00000 000000
Example: OneUp $t0, $t1, $t2

Name: TooBad
Function: Sub
Binary: 000000 sssss ttttt fffff 00000 000001
Example: TooBad $t0, $t1, $t2

Name: CoinCollect
Function: Addi
Binary: 000000 sssss fffff tttttttttttttttt
Example: CoinCollect $t0, 100

Name: CosmicComet
Function: Mult
Binary: 000000 sssss ttttt fffff 00000 000100
Example: CosmicComet $t1, $t2

Name: CosmicMario
Function: Mfhi
Binary: 000000 sssss ttttt fffff 00000 001000
Example: CosmicMario $t0

Name: CosmicLuigi
Function: Mflo
Binary: 000000 sssss ttttt fffff 00000 010000
Example: CosmicLuigi $t0

Name: SplitLaunchStar
Function: Div
Binary: 000000 sssss ttttt fffff 00000 100000
Example: SplitLaunchStar $t0, $t1

Name: HungryLuma
Function: Beq
Binary: 000000 sssss fffff tttttttttttttttt
Example: HungryLuma $t0, $t1, LABEL

Name: DomeAccess
Function: Bgt
Binary: 000000 sssss fffff tttttttttttttttt
Example: DomeAccess $t0, $t1, LABEL

Name: DomeLocked
Function: Blt
Binary: 000000 sssss fffff tttttttttttttttt
Example: DomeLocked $t0, $t1, LABEL

Name: LaunchStar
Function: j
Binary: 000000 ffffffffffffffffffffffffff
Example: LaunchStar LABEL

Name: PlanetJump
Function: jal
Binary: 111111 ffffffffffffffffffffffffff
Example: PlanetJump LABEL

Custom Instructions:
000000(opp) 00000(rs) 00000(rt) 0000000000000000(imm)

Name: StarbitCollect
Function: Functions as addi, but only for positives
Binary: 000001 sssss fffff tttttttttttttttt
Example: StarbitCollect $t0, $t1, 100

Name: StarbitShoot
Function: Functions as addi, but only for negatives
Binary: 000010 sssss fffff tttttttttttttttt
Example: StarbitShoot $t0, $t1,  -100

Name: LumaTransform
Function: Print a message from a label then jump to $ra
Binary: 000100 00000 00000 ffffffffffffffff
Example: LumaTransform LABEL

Name: Pullstar
Function: Returns to the jump register location (without printing a message)
Binary: 001000 00000 00000 0000000000000000
Example: Pullstar

Name: SpeedrunComet
Function: Loop for the specified amount of time (a number stored in $t0) to a specified label.
Binary: 010000 ffffffffffffffffffffffffff
Example: SpeedrunComet LABEL

Name: Spin
Function: Rotates bits left twice
Binary: 100000 00000 ttttt 00000 00000 000000
Example: Spin $t0

Name: GreenStars
Function: Random 1 in 3 chance to add 1 to the given register
Binary: 000011 00000 00000 fffff 00000 000000
Example: GreenStars $t0

Name: RollingBall
Function: Jumps to a random line either forward two or backward two
Binary: 000101 00000 00000 0000000000000000
Example: RollingBall

Name: PurpleComet
Function: Return a random number between 0 and 100
Binary: 001001 00000 00000 fffff 00000 000000
Example: PurpleComet $t0

Name: Talk
Function: Print 1 of 3 messages randomly
Binary: 010001 00000 00000 0000000000000000
Example: Talk
