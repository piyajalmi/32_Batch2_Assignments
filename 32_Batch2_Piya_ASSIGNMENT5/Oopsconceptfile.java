//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:ASSIGNMENT 5
//START DATE:30 SEPETEMBER 2024
//MODIFIED DATE:30 SEPTEMBER 2024
//DESCRIPTION: THIS IS A SAME CODE USED FOR ASSIGNMENT 1 IN WHICH ALL CONCEPTS OF OBJECT ORIENTED PROGRAMMING HAS BEEN IMPLEMENTED WHICH WAS TAUGHT IN CLASS.
import java.util.*;

// Abstract class (Abstraction)
abstract class Member {
    protected String name;
    protected String contactInfo;

    public Member(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}

// MLA inherits Member (Inheritance)
class MLA extends Member {
    private Constituency constituency;
    private PoliticalParty party;

    public MLA(String name, String contactInfo, Constituency constituency, PoliticalParty party) {
        super(name, contactInfo);
        this.constituency = constituency;
        this.party = party;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public PoliticalParty getParty() {
        return party;
    }

    public void introduceBill(String title, String description, Session session) {
        Bill bill = new Bill(title, description, this);
        constituency.addBill(bill);
        session.addBill(bill);
        System.out.println("Bill introduced successfully by " + name);
    }
}

// Constituency class encapsulates its data (Encapsulation)
class Constituency {
    private String name;
    private String area;
    private int population;
    private List<MLA> mlas;
    private List<Bill> bills;

    public Constituency(String name, String area, int population) {
        this.name = name;
        this.area = area;
        this.population = population;
        this.mlas = new ArrayList<>();
        this.bills = new ArrayList<>();
    }

    public void addMLA(MLA mla) {
        mlas.add(mla);
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public String getName() {
        return name;
    }

    public List<MLA> getMLAs() {
        return mlas;
    }

    public List<Bill> getBills() {
        return bills;
    }
}

// PoliticalParty class with encapsulation
class PoliticalParty {
    private String name;
    private String ideology;
    private String leader;
    private List<MLA> mlas;

    public PoliticalParty(String name, String ideology, String leader) {
        this.name = name;
        this.ideology = ideology;
        this.leader = leader;
        this.mlas = new ArrayList<>();
    }

    public void addMLA(MLA mla) {
        mlas.add(mla);
    }

    public String getName() {
        return name;
    }

    public List<MLA> getMLAs() {
        return mlas;
    }
}

// Bill class
class Bill {
    private String title;
    private String description;
    private MLA sponsor;
    private boolean passed;

    public Bill(String title, String description, MLA sponsor) {
        this.title = title;
        this.description = description;
        this.sponsor = sponsor;
        this.passed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

// Session class
class Session {
    private Date date;
    private List<Bill> bills;

    public Session(Date date) {
        this.date = date;
        this.bills = new ArrayList<>();
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public Date getDate() {
        return date;
    }

    public List<Bill> getBills() {
        return bills;
    }
}

// Main class with menu-driven code
public class Oopsconceptfile {

    public static void main(String[] args) {
        Constituency constituency1 = new Constituency("Panaji", "Urban", 150000);
        Constituency constituency2 = new Constituency("Margao", "Suburban", 200000);

        PoliticalParty partyA = new PoliticalParty("Party A", "Progressive", "Leader A");
        PoliticalParty partyB = new PoliticalParty("Party B", "Conservative", "Leader B");

        MLA mla1 = new MLA("MLA 1", "1234567890", constituency1, partyA);
        MLA mla2 = new MLA("MLA 2", "9876543210", constituency2, partyB);

        constituency1.addMLA(mla1);
        constituency2.addMLA(mla2);

        partyA.addMLA(mla1);
        partyB.addMLA(mla2);

        Session session1 = new Session(new Date());
        Session session2 = new Session(new Date());

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nGoa Legislative Assembly Menu:");
            System.out.println("1. View MLAs");
            System.out.println("2. View Bills in a Session");
            System.out.println("3. Introduce a Bill");
            System.out.println("4. Add MLA");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // View MLAs in both constituencies
                    System.out.println("\nMLAs in constituency 1:");
                    for (MLA mla : constituency1.getMLAs()) {
                        System.out.println(mla.getName() + " - Constituency: " + mla.getConstituency().getName()
                                + ", Party: " + mla.getParty().getName());
                    }
                    System.out.println("\nMLAs in constituency 2:");
                    for (MLA mla : constituency2.getMLAs()) {
                        System.out.println(mla.getName() + " - Constituency: " + mla.getConstituency().getName()
                                + ", Party: " + mla.getParty().getName());
                    }
                    break;
                case 2:
                    // View Bills in a session
                    System.out.println("\nBills in Session 1:");
                    for (Bill bill : session1.getBills()) {
                        System.out.println(bill.getTitle() + " - " + bill.getDescription());
                    }
                    System.out.println("\nBills in Session 2:");
                    for (Bill bill : session2.getBills()) {
                        System.out.println(bill.getTitle() + " - " + bill.getDescription());
                    }
                    break;
                case 3:
                    // Introduce a new bill
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter bill title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter bill description: ");
                    String description = scanner.nextLine();
                    mla1.introduceBill(title, description, session1); // Introducing bill in session 1 by MLA 1
                    break;
                case 4:
                    // Add a new MLA
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter MLA name: ");
                    String mlaName = scanner.nextLine();
                    System.out.print("Enter contact info: ");
                    String contactInfo = scanner.nextLine();
                    System.out.print("Enter constituency (1 for Panaji, 2 for Margao): ");
                    int constituencyChoice = scanner.nextInt();
                    System.out.print("Enter party (1 for Party A, 2 for Party B): ");
                    int partyChoice = scanner.nextInt();

                    Constituency selectedConstituency = (constituencyChoice == 1) ? constituency1 : constituency2;
                    PoliticalParty selectedParty = (partyChoice == 1) ? partyA : partyB;

                    MLA newMLA = new MLA(mlaName, contactInfo, selectedConstituency, selectedParty);
                    selectedConstituency.addMLA(newMLA);
                    selectedParty.addMLA(newMLA);

                    System.out.println("MLA added successfully.");
                    break;
                case 5:
                    // Exit the program
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }
}
