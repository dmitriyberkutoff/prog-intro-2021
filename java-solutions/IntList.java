import java.util.Arrays;

public class IntList {
    int[][] numbers;
    int globalIndex = 0;
    int curIndex = 0;

    public IntList() {
        numbers = new int[2][1];
    }

    public void appendGlobal(int num) {
        if (numbers[0].length == globalIndex) {
            numbers[0] = Arrays.copyOf(numbers[0], 2*numbers[0].length);
        }
        numbers[0][globalIndex] = num;
        globalIndex++;
    }

    public void increaseCurrent() {
        if (numbers[1].length == curIndex){
            numbers[1] = Arrays.copyOf(numbers[1], 2*numbers[1].length);
        }
        numbers[1][curIndex] = numbers[1][curIndex-1]+1;
        curIndex++;
    }

    public void putOne() {
        if (numbers[1].length == curIndex){
            numbers[1] = Arrays.copyOf(numbers[1], 2*numbers[1].length);
        }
        numbers[1][curIndex] = 1;
        curIndex++;
    }

    public int[] getGlobal(){
        return Arrays.copyOf(numbers[0], globalIndex);
    }

    public int[] getCurrent() {
        return  Arrays.copyOf(numbers[1], curIndex);
    }

    public int getValueGlobal(int indexValue){
        return numbers[0][indexValue];
    }

    public int getValueCurrent(int indexValue){
        return numbers[1][indexValue];
    }
}