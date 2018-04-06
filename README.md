# E-Commerce-Shop-Back-End
Spring REST service for e commerce shop. The shop have two type of users admins and normal users. Admins can create, edit or delete categories and products. They can check sent orders by users and to complete them. Users have shopping cart. They can search products by categories or by keywords and to order them. All the products are paginated.
<br/>

## Technologies used:
Spring MVC, Spring Boot, Spring Data, Spring Security, JSON, MySQL, JWT(Json Web Token).
<br/>

### POST /register -> Register new user.
Required parameters -> firstName, lastName, username, email, password, country, address, phone.

#### Successful register response:
```JavaScript
{
    "message": "You have been registered successful",
    "error": false
}
```
<br/>

### POST /login -> Login in your account.
Required parameters -> username and password.

#### Successful login response:
```JavaScript
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXVkIjoid2ViIiwiZXhwIjoxNTIzNjU3ODI0LCJpYXQiOjE1MjMwNTMwMjR9.SanbUbyaKcGOmCxYCnmrBKI9_aSAu__Vg18CzN5XBoAU9JohkbdS38apLwHmokcocdjImScz2qnG57we_ahohA"
}
```
<br/>

