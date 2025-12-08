package mars.mips.instructions.customlangs;
import mars.simulator.*;
import mars.mips.hardware.*;
import mars.mips.instructions.syscalls.*;
import mars.*;
import mars.util.*;
import java.util.*;
import java.io.*;
import mars.mips.instructions.*;
import java.util.Random;


public class MarioGalaxyLanguage extends CustomAssembly {
    @Override
    public String getName() {
        return "Super Mario Galaxy Assembly";
    }

    @Override
    public String getDescription(){
        return "Create code with all the fun of Super Mario Galaxy, the early 2000s " +
                "masterpiece platformer created by Nintendo! Has different functions " +
                "named after and based on (functionality-wise) various aspects of the " +
                "Super Mario Galaxy game.";
    }

    @Override
    protected void populate(){
        instructionList.add(
                new BasicInstruction("OneUp $t1,$t2,$t3",
                        "Gain an additional Mario! : set $t1 to ($t2 plus $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = RegisterFile.getValue(operands[2]);
                                int sum = add1 + add2;
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("TooBad $t1,$t2,$t3",
                        "You died! : set $t1 to ($t2 minus $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int sub1 = RegisterFile.getValue(operands[1]);
                                int sub2 = RegisterFile.getValue(operands[2]);
                                int dif = sub1 - sub2;
                                if ((sub1 >= 0 && sub2 < 0 && dif < 0)
                                        || (sub1 < 0 && sub2 >= 0 && dif >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], dif);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("CoinCollect $t1,$t2,-100",
                        "Ba-ding! : set $t1 to ($t2 plus signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "000000 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = operands[2] << 16 >> 16;
                                int sum = add1 + add2;
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("CosmicComet $t1,$t2",
                        "Create multiple cosmic clones! : Set hi to high-order 32 bits, lo to low-order 32 bits of the product of " +
                                "$t1 and $t2 (use mfhi to access hi, mflo to access lo)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000100",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                long product = (long) RegisterFile.getValue(operands[0])
                                        * (long) RegisterFile.getValue(operands[1]);
                                RegisterFile.updateRegister(33, (int) (product >> 32));
                                RegisterFile.updateRegister(34, (int) ((product << 32) >> 32));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("CosmicMario $t1",
                        "Uses cosmic tricks to move from HI register : Set $t1 to contents of HI (see multiply and divide operations)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(33));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("CosmicLuigi $t1",
                        "Uses cosmic tricks to move from LO register : Set $t1 to contents of LO (see multiply and divide operations)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 010000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(34));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("SplitLaunchStar $t1,$t2",
                        "Find the divided pieces! : Divide $t1 by $t2 then set LO to quotient and HI to remainder (use mfhi to access HI, mflo to access LO)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                if (RegisterFile.getValue(operands[1]) == 0)
                                {
                                    return;
                                }

                                RegisterFile.updateRegister(33,
                                        RegisterFile.getValue(operands[0])
                                                % RegisterFile.getValue(operands[1]));
                                RegisterFile.updateRegister(34,
                                        RegisterFile.getValue(operands[0])
                                                / RegisterFile.getValue(operands[1]));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("HungryLuma $t1,$t2,label",
                        "Branch if you have enough starbits! : Branch to statement at label's address if $t1 and $t2 are equal",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000000 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        == RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("DomeAccess $t1,$t2,label",
                        "Branch if you have enough stars to enter the dome! : Branch to statement at label's address if $t1 > $t2",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000000 sssss fffff tttttttttttttttt",
                        new SimulationCode(){
                            public void simulate(ProgramStatement statement) throws ProcessingException{
                                int[] operands = statement.getOperands();

                                if(RegisterFile.getValue(operands[0]) > RegisterFile.getValue(operands[1])){
                                    Globals.instructionSet.processBranch(operands[2]);

                                }

                            }
                        }
        ));

        instructionList.add(
                new BasicInstruction("DomeLocked $t1,$t2,label",
                        "Branch somewhere else if you don't have enough stars to enter the dome! : Branch to statement at label's address if $t1 < $t2",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000000 sssss fffff tttttttttttttttt",
                        new SimulationCode(){
                            public void simulate(ProgramStatement statement) throws ProcessingException{
                                int[] operands = statement.getOperands();

                                if(RegisterFile.getValue(operands[0]) < RegisterFile.getValue(operands[1])){
                                    Globals.instructionSet.processBranch(operands[2]);

                                }

                            }
                        }
                ));

        instructionList.add(
                new BasicInstruction("LaunchStar target",
                        "Launch to a different planet! : Jump to statement at target address",
                        BasicInstructionFormat.J_FORMAT,
                        "000000 ffffffffffffffffffffffffff",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                Globals.instructionSet.processJump(
                                        ((RegisterFile.getProgramCounter() & 0xF0000000)
                                                | (operands[0] << 2)));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("PlanetJump target",
                        "Leap around a small planet, end up back where you started! : Jump to statement and store the original address in $ra",
                        BasicInstructionFormat.J_FORMAT,
                        "111111 ffffffffffffffffffffffffff",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                Globals.instructionSet.processReturnAddress(31);
                                Globals.instructionSet.processJump(
                                        (RegisterFile.getProgramCounter() & 0xF0000000)
                                                | (operands[0] << 2));
                            }
                        }));

    instructionList.add(
            new BasicInstruction("StarbitCollect $t1,$t2,-100",
        "Collect starbits to fire at your enemies (or hungry lumas)! : set $t1 to ($t2 plus a positive 16-bit immediate)",
    BasicInstructionFormat.I_FORMAT,
            "000001 sssss fffff tttttttttttttttt",
            new SimulationCode(){
        public void simulate(ProgramStatement statement) throws ProcessingException
        {
            int[] operands = statement.getOperands();
            int add1 = RegisterFile.getValue(operands[1]);
            int add2 = operands[2] << 16 >> 16;
            int sum = add1 + add2;
            if(add2 < 0){
                throw new ProcessingException(statement, "To lose starbits, shoot them!", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
            }
            if ((add1 >= 0 && add2 >= 0 && sum < 0)
                    || (add1 < 0 && add2 < 0 && sum >= 0))
            {
                throw new ProcessingException(statement,
                        "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
            }
            RegisterFile.updateRegister(operands[0], sum);
        }
    }));

instructionList.add(
        new BasicInstruction("StarbitShoot $t1,$t2,-100",
        "Delicious! : set $t1 to ($t2 minus 16-bit immediate)",
    BasicInstructionFormat.I_FORMAT,
            "000010 sssss fffff tttttttttttttttt",
            new SimulationCode(){
        public void simulate(ProgramStatement statement) throws ProcessingException
        {
            int[] operands = statement.getOperands();
            int add1 = RegisterFile.getValue(operands[1]);
            int add2 = operands[2] << 16 >> 16;
            int sum = add1 + add2;
            if(add2 > 0){
                throw new ProcessingException(statement, "You won't gain starbits by shooting them!", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
            }
            if ((add1 >= 0 && add2 >= 0 && sum < 0)
                    || (add1 < 0 && add2 < 0 && sum >= 0))
            {
                throw new ProcessingException(statement,
                        "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
            }
            RegisterFile.updateRegister(operands[0], sum);
        }
    }));

instructionList.add(
        new BasicInstruction("LumaTransform target",
        "TRANSFOOOOOOOOORM : Print a string from a memory address, then return to $ra",
    BasicInstructionFormat.I_FORMAT,
            "000100 00000 00000 ffffffffffffffff",
            new SimulationCode(){
        public void simulate(ProgramStatement statement) throws ProcessingException {
            char CurrentChar = 0;
            String label = statement.getOriginalTokenList().get(1).getValue();
            int byteAddress = Globals.program.getLocalSymbolTable().getAddressLocalOrGlobal(label);
            try {
                CurrentChar = (char) Globals.memory.getByte(byteAddress);
                while (CurrentChar != 0) {
                    SystemIO.printString(new Character(CurrentChar).toString());
                    byteAddress++;
                    CurrentChar = (char) Globals.memory.getByte(byteAddress);
                }
            } catch (AddressErrorException error) {
                throw new ProcessingException(statement, error);
            }
            int ra = RegisterFile.getValue(31);
            Globals.instructionSet.processJump(ra);
        }
        }));
instructionList.add(
        new BasicInstruction("Pullstar",
                "Pull yourself back to safety! : Return to $ra",
                BasicInstructionFormat.R_FORMAT,
                "001000 00000 00000 0000000000000000",
                        new SimulationCode(){
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int ra = RegisterFile.getValue(31);
                                Globals.instructionSet.processJump(ra);
                            }
                            }));

instructionList.add(
        new BasicInstruction(
                "SpeedrunComet target",
                "Beat the clock! : Loop for the specified amount of time (a number stored in $t0) to a specified label.",
                BasicInstructionFormat.J_FORMAT,
                "010000 ffffffffffffffffffffffffff",
                new SimulationCode(){
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int counterReg = RegisterFile.getValue(8);
                        counterReg -= 1;
                        RegisterFile.updateRegister(8, counterReg);
                        if(counterReg != 0){
                            Globals.instructionSet.processJump(((RegisterFile.getProgramCounter() & 0xF0000000)
                                    | (operands[0] << 2)));
                        }

                    }
                }));

        instructionList.add(
                new BasicInstruction(
                        "Spin $t0",
                        "Spin to gain extra height or defeat your enemies! : Shift the bits stored in a regerster over twice",
                        BasicInstructionFormat.R_FORMAT,
                        "100000 00000 00000 fffff 00000 000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int valueToSpin = RegisterFile.getValue(operands[0]);
                                int shift = valueToSpin << 2;
                                RegisterFile.updateRegister(operands[0], shift);
                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "GreenStar $t0",
                        "Find the hidden green star! : Random 1 in 3 chance to add 1 to the given register",
                        BasicInstructionFormat.R_FORMAT,
                        "000011 00000 00000 fffff 00000 000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                Random random = new Random();

                                int chanceForUpdate = random.nextInt(3);

                                if(chanceForUpdate == 0){
                                    int value = RegisterFile.getValue(operands[0]) + 1;

                                    RegisterFile.updateRegister(operands[0], value);

                                }

                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "RollingBall",
                        "Balance and jump to dodge enemies n obstacles to get to the end! : Jumps to a random line either forward two or backward two",
                        BasicInstructionFormat.R_FORMAT,
                        "000101 00000 00000 0000000000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                Random random = new Random();
                                int newDirection;
                                if(random.nextInt(2) == 0){
                                    newDirection = 2;
                                }else{
                                    newDirection = -2;
                                }

                                int currentLocation = RegisterFile.getProgramCounter();
                                int newTarget = currentLocation + (newDirection * 4);

                                RegisterFile.setProgramCounter(newTarget);
                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "PurpleComet $t0",
                        "Collect all 100 coins! : Return a random number between 0 and 100",
                        BasicInstructionFormat.R_FORMAT,
                        "001001 00000 00000 fffff 00000 000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                int register = operands[0];
                                Random random = new Random();

                                int randomCoinVal = random.nextInt(101);

                                RegisterFile.updateRegister(register, randomCoinVal);
                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "talk",
                        "Say hi!! : Print 1 of 3 messages randomly!",
                        BasicInstructionFormat.R_FORMAT,
                        "010001 00000 00000 00000 00000 000000",
                        new SimulationCode() {

                            private final String[] messages = {"Hello there!", "Message test!!!!!", "HOORAY!!!"};
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                Random random = new Random();

                                int messageChoice = random.nextInt(messages.length);
                                String messageChosen = messages[messageChoice];

                                SystemIO.printString(messageChosen);

                            }
                        }));
}
}