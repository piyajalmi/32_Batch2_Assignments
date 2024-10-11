# AUTHOR: PIYA JALMI
# ROLL NO: 32
# TITLE: GOA LEGISLATIVE ASSEMBLY
# START DATE: 8 JULY 2024
# MODIFIED DATE: 8 JULY 2024
# DESCRIPTION: THIS IS A MENU DRIVEN CODE OF GOA LEGISLATIVE ASSEMBLY WHICH HELPS YOU TO VIEW MLAS, ADD MLAS, VIEW WHAT ARE THE BILL INTRODUCED AND ALSO ISSUE A BILL

class Member:
    def __init__(self, name, contact_info):
        self.name = name
        self.contact_info = contact_info

    def get_name(self):
        return self.name

    def get_contact_info(self):
        return self.contact_info


class MLA(Member):
    def __init__(self, name, contact_info, constituency, party):
        super().__init__(name, contact_info)
        self.constituency = constituency
        self.party = party

    def get_constituency(self):
        return self.constituency

    def get_party(self):
        return self.party

    def introduce_bill(self, title, description, session):
        bill = Bill(title, description, self)
        self.constituency.add_bill(bill)
        session.add_bill(bill)
        print(f"Bill introduced successfully by {self.name}")


class Constituency:
    def __init__(self, name, area, population):
        self.name = name
        self.area = area
        self.population = population
        self.mlas = []
        self.bills = []

    def add_mla(self, mla):
        self.mlas.append(mla)

    def add_bill(self, bill):
        self.bills.append(bill)

    def get_name(self):
        return self.name

    def get_mlas(self):
        return self.mlas

    def get_bills(self):
        return self.bills


class PoliticalParty:
    def __init__(self, name, ideology, leader):
        self.name = name
        self.ideology = ideology
        self.leader = leader
        self.mlas = []

    def add_mla(self, mla):
        self.mlas.append(mla)

    def get_name(self):
        return self.name

    def get_mlas(self):
        return self.mlas


class Bill:
    def __init__(self, title, description, sponsor):
        self.title = title
        self.description = description
        self.sponsor = sponsor
        self.passed = False

    def get_title(self):
        return self.title

    def get_description(self):
        return self.description


class Session:
    def __init__(self, date):
        self.date = date
        self.bills = []

    def add_bill(self, bill):
        self.bills.append(bill)

    def get_date(self):
        return self.date

    def get_bills(self):
        return self.bills


def main():
    from datetime import date

    constituency1 = Constituency("Panaji", "Urban", 150000)
    constituency2 = Constituency("Margao", "Suburban", 200000)

    partyA = PoliticalParty("Party A", "Progressive", "Leader A")
    partyB = PoliticalParty("Party B", "Conservative", "Leader B")

    mla1 = MLA("MLA 1", "1234567890", constituency1, partyA)
    mla2 = MLA("MLA 2", "9876543210", constituency2, partyB)

    constituency1.add_mla(mla1)
    constituency2.add_mla(mla2)

    partyA.add_mla(mla1)
    partyB.add_mla(mla2)

    session1 = Session(date.today())
    session2 = Session(date.today())

    constituency1.add_bill(Bill("Education Reform Bill", "To improve educational standards", mla1))
    constituency2.add_bill(Bill("Healthcare Bill", "To enhance healthcare services", mla2))

    session1.add_bill(constituency1.get_bills()[0])
    session2.add_bill(constituency2.get_bills()[0])

    while True:
        print("\nGoa Legislative Assembly Menu:")
        print("1. View MLAs")
        print("2. View Bills in a Session")
        print("3. Introduce a Bill")
        print("4. Add MLAS")
        print("5. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            print("\nMLAs in constituency 1:")
            for mla in constituency1.get_mlas():
                print(f"{mla.get_name()} - Constituency: {mla.get_constituency().get_name()}, Party: {mla.get_party().get_name()}")

            print("\nMLAs in constituency 2:")
            for mla in constituency2.get_mlas():
                print(f"{mla.get_name()} - Constituency: {mla.get_constituency().get_name()}, Party: {mla.get_party().get_name()}")

        elif choice == 2:
            print("\nBills in Session 1:")
            for bill in session1.get_bills():
                print(f"{bill.get_title()} - {bill.get_description()}")

        elif choice == 3:
            title = input("Enter bill title: ")
            description = input("Enter bill description: ")
            mla1.introduce_bill(title, description, session1)
            mla2.introduce_bill(title, description, session2)

        elif choice == 4:
            mla_name = input("Enter MLA name: ")
            contact_info = input("Enter contact info: ")
            constituency_choice = int(input("Enter constituency (1 for Panaji, 2 for Margao): "))
            party_choice = int(input("Enter party (1 for Party A, 2 for Party B): "))

            selected_constituency = constituency1 if constituency_choice == 1 else constituency2
            selected_party = partyA if party_choice == 1 else partyB

            new_mla = MLA(mla_name, contact_info, selected_constituency, selected_party)
            selected_constituency.add_mla(new_mla)
            selected_party.add_mla(new_mla)

            print("MLA added successfully.")

        elif choice == 5:
            print("Exiting...")
            break

        else:
            print("Invalid choice. Please enter again.")


if __name__ == "__main__":
    main()
