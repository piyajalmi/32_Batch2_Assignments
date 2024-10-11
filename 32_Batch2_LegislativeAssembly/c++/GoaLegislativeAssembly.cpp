#include <iostream>
#include <string>
#include <vector>
#include <memory>

using namespace std;

// Forward declaration of classes
class Bill;
class Session;

// THIS IS THE ABSTRACT CLASS WHICH PROVIDES A GENERAL TEMPLATE FOR THE OTHER CLASSES
class Member {
protected:
    std::string name;
    std::string contactInfo;

public:
    Member(const std::string& name, const std::string& contactInfo)
        : name(name), contactInfo(contactInfo) {}

    virtual ~Member() = default; // Make the class polymorphic

    std::string getName() const { return name; }
    std::string getContactInfo() const { return contactInfo; }
};


// THIS CLASS REPRESENTS A BILL
class Bill {
private:
    string title;
    string description;
    shared_ptr<Member> sponsor;
    bool passed;

public:
    Bill(const string& title, const string& description, shared_ptr<Member> sponsor)
        : title(title), description(description), sponsor(sponsor), passed(false) {}

    string getTitle() const {
        return title;
    }

    string getDescription() const {
        return description;
    }
};

// THIS CLASS REPRESENTS A CONSTITUENCY
class Constituency {
private:
    string name;
    string area;
    int population;
    vector<shared_ptr<Member>> mlas;
    vector<shared_ptr<Bill>> bills;

public:
    Constituency(const string& name, const string& area, int population)
        : name(name), area(area), population(population) {}

    void addMLA(shared_ptr<Member> mla) {
        mlas.push_back(mla);
    }

    void addBill(shared_ptr<Bill> bill) {
        bills.push_back(bill);
    }

    string getName() const {
        return name;
    }

    vector<shared_ptr<Member>> getMLAs() const {
        return mlas;
    }

    vector<shared_ptr<Bill>> getBills() const {
        return bills;
    }
};

// THIS CLASS REPRESENTS A POLITICAL PARTY
class PoliticalParty {
private:
    string name;
    string ideology;
    string leader;
    vector<shared_ptr<Member>> mlas;

public:
    PoliticalParty(const string& name, const string& ideology, const string& leader)
        : name(name), ideology(ideology), leader(leader) {}

    void addMLA(shared_ptr<Member> mla) {
        mlas.push_back(mla);
    }

    string getName() const {
        return name;
    }

    vector<shared_ptr<Member>> getMLAs() const {
        return mlas;
    }
};

// THIS CLASS REPRESENTS A SESSION
class Session {
private:
    vector<shared_ptr<Bill>> bills;

public:
    Session() {}

    void addBill(shared_ptr<Bill> bill) {
        bills.push_back(bill);
    }

    vector<shared_ptr<Bill>> getBills() const {
        return bills;
    }
};

// THIS CLASS EXTENDS THE MEMBER CLASS, INHERITING ITS ATTRIBUTES AND ADDING ADDITIONAL ATTRIBUTES
class MLA : public Member, public enable_shared_from_this<MLA> {
private:
    shared_ptr<Constituency> constituency;
    shared_ptr<PoliticalParty> party;

public:
    MLA(const string& name, const string& contactInfo, shared_ptr<Constituency> constituency, shared_ptr<PoliticalParty> party)
        : Member(name, contactInfo), constituency(constituency), party(party) {}

    shared_ptr<Constituency> getConstituency() const {
        return constituency;
    }

    shared_ptr<PoliticalParty> getParty() const {
        return party;
    }

    void introduceBill(const string& title, const string& description, shared_ptr<Session> session) {
        shared_ptr<Bill> bill = make_shared<Bill>(title, description, shared_from_this());
        constituency->addBill(bill);
        session->addBill(bill);
        cout << "Bill introduced successfully by " << name << endl;
    }
};

// MAIN DRIVER CLASS
int main() {
    auto constituency1 = make_shared<Constituency>("Panaji", "Urban", 150000);
    auto constituency2 = make_shared<Constituency>("Margao", "Suburban", 200000);

    auto partyA = make_shared<PoliticalParty>("Party A", "Progressive", "Leader A");
    auto partyB = make_shared<PoliticalParty>("Party B", "Conservative", "Leader B");

    auto mla1 = make_shared<MLA>("MLA 1", "1234567890", constituency1, partyA);
    auto mla2 = make_shared<MLA>("MLA 2", "9876543210", constituency2, partyB);

    constituency1->addMLA(mla1);
    constituency2->addMLA(mla2);

    partyA->addMLA(mla1);
    partyB->addMLA(mla2);

    auto session1 = make_shared<Session>();
    auto session2 = make_shared<Session>();

    auto bill1 = make_shared<Bill>("Education Reform Bill", "To improve educational standards", mla1);
    auto bill2 = make_shared<Bill>("Healthcare Bill", "To enhance healthcare services", mla2);

    constituency1->addBill(bill1);
    constituency2->addBill(bill2);

    session1->addBill(bill1);
    session2->addBill(bill2);

    int choice;
    do {
        cout << "\nGoa Legislative Assembly Menu:" << endl;
        cout << "1. View MLAs" << endl;
        cout << "2. View Bills in a Session" << endl;
        cout << "3. Introduce a Bill" << endl;
        cout << "4. Add MLAs" << endl;
        cout << "5. Exit" << endl;
        cout << "Enter your choice: ";
        cin >> choice;

        switch (choice) {
            case 1: {
                cout << "\nMLAs in constituency 1:" << endl;
                for (const auto& member : constituency1->getMLAs()) {
                    auto mla = dynamic_pointer_cast<MLA>(member);
                    if (mla) {
                        cout << mla->getName() << " - Constituency: " << mla->getConstituency()->getName()
                             << ", Party: " << mla->getParty()->getName() << endl;
                    }
                }
                cout << "\nMLAs in constituency 2:" << endl;
                for (const auto& member : constituency2->getMLAs()) {
                    auto mla = dynamic_pointer_cast<MLA>(member);
                    if (mla) {
                        cout << mla->getName() << " - Constituency: " << mla->getConstituency()->getName()
                             << ", Party: " << mla->getParty()->getName() << endl;
                    }
                }
                break;
            }
            case 2: {
                cout << "\nBills in Session 1:" << endl;
                for (const auto& bill : session1->getBills()) {
                    cout << bill->getTitle() << " - " << bill->getDescription() << endl;
                }
                break;
            }
            case 3: {
                cin.ignore(); // Ignore the newline character left in the buffer
                string title, description;
                cout << "Enter bill title: ";
                getline(cin, title);
                cout << "Enter bill description: ";
                getline(cin, description);
                mla1->introduceBill(title, description, session1);
                break;
            }
            case 4: {
                cin.ignore(); // Ignore the newline character left in the buffer
                string mlaName, contactInfo;
                int constituencyChoice, partyChoice;

                cout << "Enter MLA name: ";
                getline(cin, mlaName);
                cout << "Enter contact info: ";
                getline(cin, contactInfo);
                cout << "Enter constituency (1 for Panaji, 2 for Margao): ";
                cin >> constituencyChoice;
                cout << "Enter party (1 for Party A, 2 for Party B): ";
                cin >> partyChoice;

                shared_ptr<Constituency> selectedConstituency = (constituencyChoice == 1) ? constituency1 : constituency2;
                shared_ptr<PoliticalParty> selectedParty = (partyChoice == 1) ? partyA : partyB;

                auto newMLA = make_shared<MLA>(mlaName, contactInfo, selectedConstituency, selectedParty);
                selectedConstituency->addMLA(newMLA);
                selectedParty->addMLA(newMLA);

                cout << "MLA added successfully." << endl;
                break;
            }
            case 5:
                cout << "Exiting..." << endl;
                break;
            default:
                cout << "Invalid choice. Please enter again." << endl;
                break;
        }
    } while (choice != 5);

    return 0;
}
