import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestLoanClassStorage {
    private static List<Loan> allLoans = new ArrayList<>();
    private static List<Loan> savedLoans = new ArrayList<>();
    public static void main(String[] args) {
        boolean mainMenu = true;


        File file = new File("src/loans.ser");
        try {
            if(file.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                Loan loan = (Loan) objectInputStream.readObject();
                //savedLoans.add(loan);
                allLoans.add(loan);
            }
        } catch (EOFException e) {
            System.out.println("Loans loaded from file.\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Loan class not found!");
            e.printStackTrace();
            System.exit(0);
        }

        Scanner input = new Scanner(System.in);

        while (mainMenu) {
            System.out.println("Main menu - select an option");
            System.out.println("1... Create new loan object");
            System.out.println("2... View all loan objects");
            System.out.println("3... View a selection of loan objects");
            System.out.println("4... Save all loan objects to file");
            System.out.println("5... Save a selection of loan objects to file");
            System.out.println("6... Exit");

            int option = input.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Annual interest rate: ");
                    double annualInterestRate = input.nextDouble();

                    System.out.print("Number of years to repay: ");
                    int numberOfYears = input.nextInt();

                    System.out.print("Loan amount: ");
                    double loanAmount = input.nextDouble();

                    Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
                    allLoans.add(loan);
                    break;

                case 2:
                    int i = 0;
                    for (Loan obj : allLoans) {
                        System.out.println("Loan id: " + i);
                        System.out.printf("The loan was created on: %s\n" + "The monthly payment is: %.2f\nThe total payment is: %.2f\n\n",
                                obj.getLoanDate().toString(), obj.getMonthlyPayment(), obj.getTotalPayment());
                        i++;
                    }
                    break;

                case 3:
                    System.out.println(printAvailable());
                    System.out.print("Please enter the id's of the loan objects you want to view, separated by comma (e.g. 1,2,3): ");
                    String viewLoans = input.next();
                    String[] viewLoansArray = viewLoans.split(",");
                    List<Integer> idList = new ArrayList<>();

                    for (String view : viewLoansArray) {
                        idList.add(Integer.parseInt(view));
                    }

                    for (int print : idList) {
                        System.out.println("Loan id: " + print);
                        System.out.printf("The loan was created on: %s\n" + "The monthly payment is: %.2f\nThe total payment is: %.2f\n\n",
                                allLoans.get(print).getLoanDate().toString(), allLoans.get(print).getMonthlyPayment(), allLoans.get(print).getTotalPayment());
                    }
                    break;

                case 4:
                    System.out.println("Attempting to save all loan objects to file...");
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        for(Loan obj : allLoans) {
                            objectOutputStream.writeObject(obj);
                        }
                        fileOutputStream.close();
                        fileOutputStream.close();
                        System.out.println("All objects have saved.\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.println(printAvailable());
                    if(allLoans.size() == 0) {
                        System.out.println("Please create a new loan to save it to file.\n");
                    } else {
                        System.out.print("Please enter the id's of the loan objects you want to save, separated by comma (e.g. 1,2,3): ");
                        String saveLoans = input.next();
                        String[] saveLoansArray = saveLoans.split(",");
                        StringBuilder savedIds = new StringBuilder();

                        for (String loanId : saveLoansArray) {
                            if (!savedLoans.contains(allLoans.get(Integer.parseInt(loanId)))) {
                                savedLoans.add(allLoans.get(Integer.parseInt(loanId)));
                                savedIds.append(loanId + ", ");
                            } else {
                                System.out.println("Loan id " + loanId + " is already saved in file.");
                            }
                        }
                        boolean check = false;
                        String printIds = "";
                        if (savedIds.length() > 2) {
                            printIds = savedIds.substring(0, savedIds.length() - 2);
                            check = true;
                        }

                        try {
                            fileOutputStream = new FileOutputStream(file);
                            objectOutputStream = new ObjectOutputStream(fileOutputStream);

                            for (Loan loaan : savedLoans) {
                                objectOutputStream.writeObject(loaan);
                            }

                            fileOutputStream.close();
                            objectOutputStream.close();

                            if (check) {
                                if (printIds.length() > 1) {
                                    System.out.println("Loans with id's " + printIds + " have been added to file.\n");
                                } else {
                                    System.out.println("Loan with id " + printIds + " has been added to file.\n");
                                }
                            } else {
                                System.out.println("No loans have been updated.\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 6:
                    mainMenu = false;
                    break;

                default:
                    System.out.println("Invalid option.\n");
                    break;
            }
        }
    }

    public static String printAvailable() {
        StringBuilder available = new StringBuilder();
        int ij = 0;
        available.append("Available loan object id's: ");
        for(Loan avail : allLoans) {
            available.append(ij + ", ");
            ij++;
        }

        String printAvailable =  "";

        if(ij > 0) {
            printAvailable = available.substring(0, available.length() - 2);
        } else {
            printAvailable = "No available objects.";
        }

        return printAvailable;
    }
}