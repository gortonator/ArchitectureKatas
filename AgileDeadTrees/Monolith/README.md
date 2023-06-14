# **

## Architecture Kata: Agile Dead Trees Monolithic Design in Spring Boot

---

### Introduction
This application is an implementation of the Agile Dead Trees kata under monolithic design. Spring Boot framework is used for backend development and MySQL database is used for data storage. For security, Apache Shiro is selected to perform subject authentication and authorization, using role-based access control.

---

### Basic Functionalities
#### 1. External Subjects
**Reader**
* Browse the book variants catalog
* Put book variant in cart
* Get check out information (from cart)
* Place an order

**Writer**
* Create and submit DraftChapter for review

&nbsp;

#### 2. Internal Subjects
**User (Admin Role)**
* Browse User information in database
* Add roles to User



**User (Manager Role)**
* Modify Order status
* Create Order Track information



**User (Editor Role)**
* Review DraftBook and DraftChapter
* Publish DraftBook and DraftChapter



---

### Start Locally
To start the application locally, please follow the steps below:
1. Make sure MySQL server is running on the local machine.
2. Go to the **`application.properties`** file under `src/main/resources`:
   1. Modify `spring.datasource.url` property if needed. Here, it is using the default port `3306` and the database name is `book_company`.
   2. Modify **`spring.datasource.username`** and **`spring.datasource.password`** properties to correspond to the local MySQL database account.
   3. Modify `spring.mail.host`, `spring.mail.port`, `spring.mail.username`, and `spring.mail.password` properties. Here by default it is using the GMail server with port `587`. If you are using GMail to send out emails in the application, you could leave it as it is but you would need to use your email account and generate an app password from GMail, which could be filled in the properties of `spring.mail.username` and `spring.mail.password`. (If you do not wish to use the functionality of sending out an email when an `Order` is placed in application, just simply comment out the line of code `emailService.sendSimpleMail(emailDetails);` in `CheckoutController` class under `src/main/java/book.demo.java/controller` folder.)
3. Run **`Application.main()`**.
4. To check out the Swagger documentation of the application, simply go to `http://localhost:8080/swagger-ui/index.html` in your web browser (by default port `8080` is used to run the application).
5. To generate sample data in database, you could run the `data_generation.sql` file under the `docs` folder. By default, the database name is set to `book_company`. If you wish to use other database name, please align it with the one you set in the `application.properties` file in step 2.
6. For log in testing purpose, please use the username and password combination below:
   
   | **Subject** | **Username** | **Password**    |
   |:-----------:|:------------:|:---------------:|
   | User        | admin        | adminpassword   |
   | User        | manager      | managerpassword |
   | User        | editor       | editorpassword  |
   | Reader      | reader1      | password1       |
   | Reader      | reader2      | password2       |
   | Writer      | writer1      | password1       |
   | Writer      | writer2      | password2       |

