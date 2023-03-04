package task2.core;
import java.io.*;
import java.util.*;
import task2.toys.SoftToy;

public class Core {
    private List<SoftToy> allToys;
    private List<String> toysWon;
    Scanner in = new Scanner(System.in);
    
    public void  programLaunch(){
        this.allToys = new ArrayList<>();
        this.toysWon = new ArrayList<>();
        
        while(true){
            chooseFunctionality();
            int modeWork = readUserInput(1, 5);
            switch (modeWork) {
                case 1:
                    System.out.print("Выберите количество не больше 10!. ");
                    addToys(readUserInput(1, 10));
                    System.out.println("Игрушка успешно добавлена!");
                    break;
                case 2:
                    if(allToys.size() != 0){
                        showAllToys(1);
                        System.out.print("Введите id игрушки, чей процент хотите изменить.");
                        if(changeDropRate(readUserInput(1000, 9999))){
                            System.out.println("% выпадения изменен!");
                        }
                    }
                    else{
                        System.out.println("Автомат игрушек еще пуст! Добавьте их!");
                    }
                    break;
                case 3:
                    if(allToys.size() != 0){
                        System.out.printf("Поздравляю! Вам выпала игрушка - %s", startDrawing());
                        checkQuantity();
                    }
                    else{
                        System.out.println("Автомат игрушек еще пуст! Добавьте их! - (1)");
                    }
                    break;  
                case 4:
                    if(toysWon.size() != 0){
                        pickUpToy();
                    }
                    else{
                        System.out.println("Для начала запустите розыгрыш! - (3)");
                    }
                    break;
                case 5:
                    in.close();
                    return;     
            }
             
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    // выводит в консоль функционал приложения
    private void chooseFunctionality(){
        StringBuilder startMessage = new StringBuilder()
            .append("\n ==== \n")
            .append("Выберите режим, введя нужную цифру\n")
            .append("1 - Добавить новую игрушку \n")
            .append("2 - Изменить вероятность выпадения игрушки\n")
            .append("3 - Начать розыгрыш игрушек\n")
            .append("4 - Забрать игрушку\n")
            .append("5 - Закрыть программу\n")
            .append(" ==== \n");
        System.out.println(startMessage);
    }

    // считывает данные которые вводит пользователь в консоль, границы указаны для исользования в разных задачах
    private int readUserInput(int left, int right){
        while(true){
            try {
                System.out.print("Введите число: ");
                String result = in.nextLine();
                int resuInt = Integer.parseInt(result);
                if(resuInt >= left && resuInt <= right){
                    return resuInt;
                }
                System.out.println("Повтоите попытку");
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели неккоректное значение");            
            }
        }
    }

    // генерирует рандомные игрушки, пользователь задает только количество 
    private void addToys(int count){
        String[] animalNames  = {" dog", " cat", " mouse", " wolf", " lion", " elephant", " bear"};
        String[] animalColor = {"Red", "Green", "Pink", "Violet", "Blue", "Orange", "White"};
        Random random = new Random();
        int i = random.nextInt(0, 7);
        int j = random.nextInt(0, 7);

        int id = random.nextInt(1000, 9999);
        int probabilityGetting = random.nextInt(0, 100);
        String name = animalColor[i] + animalNames[j];

        SoftToy animal = new SoftToy(id, name, count, probabilityGetting);
        allToys.add(animal);
    }

    // выводит в консоль все игрушки, добавленные пользователем 
    private void showAllToys(int mode){
        if(mode == 1){
            System.out.println("Игрушки в автомате: ");
            for(SoftToy animal: allToys){
                System.out.println("\t" + animal.toString());
            }
        }
    }

    // замена веса игрушки по его id
    private boolean changeDropRate(int id){
        for(SoftToy animal: allToys){
            if(animal.getId() == id){
                System.out.print("Задайте % выпадения игрушки(от 0 до 99).");
                animal.setDropFrequency(readUserInput(1, 100));
                return true;
            }  
        }
        System.out.println("Игрушки с таким id нет");
        return false;
    }

    // розыгрыш игрушек согласно их весу
    private String startDrawing(){
        String[] animals = new String[allToys.size()];
        int[] weights = new int[allToys.size()];
        int index = 0;

        for(SoftToy animal: allToys){
            animals[index] = animal.getName();
            weights[index] = animal.getDropFrequency();
            index ++;
        }
        
        Random random = new Random();
        int total = Arrays.stream(weights).sum();
        int n = 0;
        int num = random.nextInt(1, total);

        for(int i = 0; i < animals.length; i++){
            n += weights[i];

            if(n >= num){
                toysWon.add(animals[i]);
                for(SoftToy anim: allToys){
                    if(anim.getName().equals(animals[i])){
                        anim.setQuantity(anim.getQuantity() - 1);
                    }
                }
                return animals[i];
            }
        }
        return null;
        
    }
    
    // проверка количества игрушек, находящихся в автомате для розыгрыша
    private void checkQuantity(){
        List<SoftToy> list1 = new ArrayList<>();
        for(SoftToy value: allToys){
            list1.add(value);
        }
            
        for(int i = 0; i < allToys.size(); i++){
            if(allToys.get(i).getQuantity() <= 0){
                list1.remove(i);
            }
        }
         allToys = list1;
    }

    // дозапиcывает\создает файл (received_toys.txt) с игрушками, которые забрал кользователь 
    private void pickUpToy(){
        try{
            FileWriter nFile = new FileWriter("task2/received_toys.txt", true);
                nFile.write(toysWon.get(0) + "\n");
                toysWon.remove(0);
                
            nFile.close();       
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
