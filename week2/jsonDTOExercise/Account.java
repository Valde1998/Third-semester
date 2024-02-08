package jsonDTOExercise;

/*
1: Json stand for JavaScript Object Notation

2: JSON doesn't use end tag
 JSON is shorter
 JSON is quicker to read and write
 JSON can use arrays
 The biggest difference is: XML has to be parsed with an XML parser. JSON can be parsed by a standard JavaScript function.

3: JSON is generally used for exchanging and storing structured data between a server and a web application.

4:The 6 data types in JSON:
1 String: A sequence of characters enclosed in double quotes, e.g., "hello world".

2 Number: A numerical value, which can be an integer or a floating-point number, e.g., 42 or 3.14.

3 Boolean: A value representing true or false.

4 Array: An ordered collection of values, enclosed in square brackets [], e.g., [1, 2, 3].

5 Object: An unordered collection of key-value pairs, enclosed in curly braces {}, e.g., {"name": "John", "age": 30}.

6 Null: A special value representing null or empty, used to indicate the absence of a value.

 5: Write down the 4 JSON syntax rules.
Data is in name/value pairs.
Data is separated by commas.
Curly braces hold objects.
Square brackets hold arrays.

 */
public class Account {

    private String firstName;
    private String lastName;
    private String birthDate;
    private Address address;
   // private BankAccount account;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    @Override
    public String toString() {
        return "Account{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address=" + address +
                '}';
    }
}
