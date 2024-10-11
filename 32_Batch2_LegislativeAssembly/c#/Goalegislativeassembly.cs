using System;
using System.Collections.Generic;

// Abstract class providing a general template for other classes
abstract class Member
{
    protected string Name;
    protected string ContactInfo;

    protected Member(string name, string contactInfo)
    {
        Name = name;
        ContactInfo = contactInfo;
    }

    public string GetName()
    {
        return Name;
    }

    public string GetContactInfo()
    {
        return ContactInfo;
    }
}

// This class extends the Member class, inheriting its attributes and adding additional attributes like Constituency and PoliticalParty
class MLA : Member
{
    private Constituency Constituency;
    private PoliticalParty Party;

    public MLA(string name, string contactInfo, Constituency constituency, PoliticalParty party)
        : base(name, contactInfo)
    {
        Constituency = constituency;
        Party = party;
    }

    public Constituency GetConstituency()
    {
        return Constituency;
    }

    public PoliticalParty GetParty()
    {
        return Party;
    }

    public void IntroduceBill(string title, string description, Session session)
    {
        Bill bill = new Bill(title, description, this);
        Constituency.AddBill(bill);
        session.AddBill(bill);
        Console.WriteLine($"Bill introduced successfully by {Name}");
    }
}

// This class represents a Constituency, which will be used when adding and viewing MLAs and Bills
class Constituency
{
    private string Name;
    private string Area;
    private int Population;
    private List<MLA> MLAs;
    private List<Bill> Bills;

    public Constituency(string name, string area, int population)
    {
        Name = name;
        Area = area;
        Population = population;
        MLAs = new List<MLA>();
        Bills = new List<Bill>();
    }

    public void AddMLA(MLA mla)
    {
        MLAs.Add(mla);
    }

    public void AddBill(Bill bill)
    {
        Bills.Add(bill);
    }

    public string GetName()
    {
        return Name;
    }

    public List<MLA> GetMLAs()
    {
        return MLAs;
    }

    public List<Bill> GetBills()
    {
        return Bills;
    }
}

// This class represents a PoliticalParty
class PoliticalParty
{
    private string Name;
    private string Ideology;
    private string Leader;
    private List<MLA> MLAs;

    public PoliticalParty(string name, string ideology, string leader)
    {
        Name = name;
        Ideology = ideology;
        Leader = leader;
        MLAs = new List<MLA>();
    }

    public void AddMLA(MLA mla)
    {
        MLAs.Add(mla);
    }

    public string GetName()
    {
        return Name;
    }

    public List<MLA> GetMLAs()
    {
        return MLAs;
    }
}

// This class represents a Bill
class Bill
{
    private string Title;
    private string Description;
    private MLA Sponsor;
    private bool Passed;

    public Bill(string title, string description, MLA sponsor)
    {
        Title = title;
        Description = description;
        Sponsor = sponsor;
        Passed = false;
    }

    public string GetTitle()
    {
        return Title;
    }

    public string GetDescription()
    {
        return Description;
    }
}

// This class represents a Session
class Session
{
    private DateTime Date;
    private List<Bill> Bills;

    public Session(DateTime date)
    {
        Date = date;
        Bills = new List<Bill>();
    }

    public void AddBill(Bill bill)
    {
        Bills.Add(bill);
    }

    public DateTime GetDate()
    {
        return Date;
    }

    public List<Bill> GetBills()
    {
        return Bills;
    }
}

// Main driver class of the code with a menu-driven interface, uses instances of other classes
class GoaLegislativeAssembly
{
    static void Main()
    {
        Constituency constituency1 = new Constituency("Panaji", "Urban", 150000);
        Constituency constituency2 = new Constituency("Margao", "Suburban", 200000);

        PoliticalParty partyA = new PoliticalParty("Party A", "Progressive", "Leader A");
        PoliticalParty partyB = new PoliticalParty("Party B", "Conservative", "Leader B");

        MLA mla1 = new MLA("MLA 1", "1234567890", constituency1, partyA);
        MLA mla2 = new MLA("MLA 2", "9876543210", constituency2, partyB);

        constituency1.AddMLA(mla1);
        constituency2.AddMLA(mla2);

        partyA.AddMLA(mla1);
        partyB.AddMLA(mla2);

        Session session1 = new Session(DateTime.Now);
        Session session2 = new Session(DateTime.Now);

        constituency1.AddBill(new Bill("Education Reform Bill", "To improve educational standards", mla1));
        constituency2.AddBill(new Bill("Healthcare Bill", "To enhance healthcare services", mla2));

        session1.AddBill(constituency1.GetBills()[0]);
        session2.AddBill(constituency2.GetBills()[0]);

        int choice;
        do
        {
            Console.WriteLine("\nGoa Legislative Assembly Menu:");
            Console.WriteLine("1. View MLAs");
            Console.WriteLine("2. View Bills in a Session");
            Console.WriteLine("3. Introduce a Bill");
            Console.WriteLine("4. Add MLAS");
            Console.WriteLine("5. Exit");
            Console.Write("Enter your choice: ");

            choice = int.Parse(Console.ReadLine());

            switch (choice)
            {
                case 1:
                    Console.WriteLine("\nMLAs in constituency 1:");
                    foreach (MLA mla in constituency1.GetMLAs())
                    {
                        Console.WriteLine($"{mla.GetName()} - Constituency: {mla.GetConstituency().GetName()}, Party: {mla.GetParty().GetName()}");
                    }

                    Console.WriteLine("\nMLAs in constituency 2:");
                    foreach (MLA mla in constituency2.GetMLAs())
                    {
                        Console.WriteLine($"{mla.GetName()} - Constituency: {mla.GetConstituency().GetName()}, Party: {mla.GetParty().GetName()}");
                    }
                    break;

                case 2:
                    Console.WriteLine("\nBills in Session 1:");
                    foreach (Bill bill in session1.GetBills())
                    {
                        Console.WriteLine($"{bill.GetTitle()} - {bill.GetDescription()}");
                    }
                    break;

                case 3:
                    Console.Write("Enter bill title: ");
                    string title = Console.ReadLine();
                    Console.Write("Enter bill description: ");
                    string description = Console.ReadLine();
                    mla1.IntroduceBill(title, description, session1);
                    break;

                case 4:
                    Console.Write("Enter MLA name: ");
                    string mlaName = Console.ReadLine();
                    Console.Write("Enter contact info: ");
                    string contactInfo = Console.ReadLine();
                    Console.Write("Enter constituency (1 for Panaji, 2 for Margao): ");
                    int constituencyChoice = int.Parse(Console.ReadLine());
                    Console.Write("Enter party (1 for Party A, 2 for Party B): ");
                    int partyChoice = int.Parse(Console.ReadLine());

                    Constituency selectedConstituency = constituencyChoice == 1 ? constituency1 : constituency2;
                    PoliticalParty selectedParty = partyChoice == 1 ? partyA : partyB;

                    MLA newMLA = new MLA(mlaName, contactInfo, selectedConstituency, selectedParty);
                    selectedConstituency.AddMLA(newMLA);
                    selectedParty.AddMLA(newMLA);

                    Console.WriteLine("MLA added successfully.");
                    break;

                case 5:
                    Console.WriteLine("Exiting...");
                    break;

                default:
                    Console.WriteLine("Invalid choice. Please enter again.");
                    break;
            }
        } while (choice != 5);
    }
}
