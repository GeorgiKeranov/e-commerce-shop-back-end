# E-Commerce-Shop-Back-End

E-commerce shop REST API created with Java and Spring Framework.

You can view the front end of this e-commerce shop that is built with Angular [here](https://github.com/GeorgiKeranov/E-Commerce-Shop-Front-End).

## Table of contents
- [Technologies used](#technologies-used)
- [Functionalities](#functionalities)
- [REST Documentation](#rest-documentation)
    - [Register new user](#register-new-user)
    - [Login in your account](#login-in-your-account)
    - [Get authenticated user's information](#get-authenticated-users-information)
    - [Get products](#get-products)
    - [Get product by id](#get-product-by-id)
    - [Get products count](#get-products-count)
    - [Get all categories](#get-all-categories)
    - [Get category by id](#get-category-by-id)
    - [Count of the order items in shopping cart of authenticated user](#count-of-the-order-items-in-shopping-cart-of-authenticated-user)
    - [Get order items in the shopping cart of authenticated user](#get-order-items-in-the-shopping-cart-of-authenticated-user)
    - [Add order item to shopping cart](#add-order-item-to-shopping-cart)
    - [Delete order item from shopping cart](#delete-order-item-from-shopping-cart)
    - [Update order item quantity](#update-order-item-quantity)
    - [Buy products from the shopping cart](#buy-products-from-the-shopping-cart)
    - [Create a new product](#create-a-new-product)
    - [Update existing product](#update-existing-product)
    - [Delete product by id](#delete-product-by-id)
    - [Create new category](#create-new-category)
    - [Update existing category](#update-existing-category)
    - [Delete existing category](#delete-existing-category)
    - [Get orders by status](#get-orders-by-status)
    - [Change order status to completed](#change-order-status-to-completed)
    - [Change order status to sent](#change-order-status-to-sent)
    - [Get user by order id](#get-user-by-order-id)
    - [Get items by order id](#get-items-by-order-id)

## Technologies used
- Java
- Spring Boot
- Spring MVC
- Spring Data
- Spring Security
- JSON
- MySQL
- JWT (Json Web Token)

## Functionalities

The project has authentication system with JWT (Json Web Token).\
Customers can register, login, add products to their cart and then purchase them.

There are two roles for users:
- Admin. Their role can:
    - Create categories and products
    - Edit categories and products
    - Delete categories and products
    - View orders from the users
    - Complete orders from the users
    - Remove orders from the users
- User. Their role can:
    - Search products by categories or by words
    - Add products to their cart
    - Change products quantity dynamically in their cart
    - Remove product dynamically in their cart.
    - Purchase the products from the cart

## REST Documentation

### Register new user
URL: `/register`\
Method: `POST`\
Required parameters: `firstName, lastName, username, email, password, country, address, phone`

#### Response:
```JavaScript
{
    "message": "You have been registered successful",
    "error": false
}
```

--------------------------------------------------

### Login in your account
URL: `/login`\
Method: `POST`\
Required parameters: `username, password`

#### Response:
```JavaScript
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXVkIjoid2ViIiwiZXhwIjoxNTIzNjU3ODI0LCJpYXQiOjE1MjMwNTMwMjR9.SanbUbyaKcGOmCxYCnmrBKI9_aSAu__Vg18CzN5XBoAU9JohkbdS38apLwHmokcocdjImScz2qnG57we_ahohA"
}
```

--------------------------------------------------

### Get authenticated user's information
URL: `/user`\
Method: `GET`\
Required parameters: `token`

#### Response:
```JavaScript
{
    "id": 3,
    "username": "john.doe",
    "country": "USA",
    "address": "st. John Doe 2",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+123456789",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ]
}
```

--------------------------------------------------

### Get products
URL: `/products`\
Method: `GET`\
Optional parameters: `searchWord, categoryId, page`

#### Response:
```JavaScript
[
    {
        "id": 15,
        "title": "Canon",
        "description": "Description here! Edited!",
        "price": 450,
        "mainImageName": "EOS-M10_0011.png",
        "categories": [
            {
                "id": 7,
                "imageName": "EOS-M10_001.png",
                "categoryName": "Cameras"
            }
        ]
    },
    {
        "id": 14,
        "title": "XMART WF430 4K",
        "description": "",
        "price": 128,
        "mainImageName": "10652475392030.jpg",
        "categories": [
            {
                "id": 8,
                "imageName": "DSC_0964__67640.1499289859.webp",
                "categoryName": "Action cameras"
            }
        ]
    },
   ...
]
```

--------------------------------------------------

### Get product by id
URL: `/products/{id}`\
Method: `GET`

#### Response:
```JavaScript
{
    "id": 13,
    "title": "SONY HDR-PJ410B",
    "description": "Resolution : 9.2 MPx\r\nType : FLASH MEMORY\r\nDisplay in inch : 2.7 \"\r\nDigital approach : x350",
    "price": 250,
    "mainImageName": "9015824646174.jpg",
    "categories": [
        {
            "id": 7,
            "imageName": "EOS-M10_001.png",
            "categoryName": "Cameras"
        }
    ]
}
```

--------------------------------------------------

### Get products count
URL: `/products/count`\
Method: `GET`\
Optional parameters: `searchWord, categoryId`

#### Response:
```JavaScript
{
    "count": 2
}
```

--------------------------------------------------

### Get all categories
URL: `/categories`\
Method: `GET`

#### Response:
```JavaScript
[
    {
        "id": 1,
        "imageName": "PCD-Filtertype-Note8-dsb.png",
        "categoryName": "Smartphones"
    },
    {
        "id": 2,
        "imageName": "yoga-900-header-hero.png",
        "categoryName": "Laptops"
    },
    ...
]
```

--------------------------------------------------

### Get category by id
URL: `/categories/{id}`\
Method: `GET`

#### Response:
```JavaScript
{
    "id": 1,
    "imageName": "PCD-Filtertype-Note8-dsb.png",
    "categoryName": "Smartphones"
}
```

--------------------------------------------------

### Count of the order items in shopping cart of authenticated user
URL: `/order/items/count`\
Method: `GET`

#### Response:
```JavaScript
{
    "count": 4
}
```

--------------------------------------------------

### Get order items in the shopping cart of authenticated user
URL: `/order/items`\
Method: `GET`

#### Response:
```JavaScript
[
    {
        "id": 12,
        "product": {
            "id": 14,
            "title": "XMART WF430 4K",
            "description": "",
            "price": 128,
            "mainImageName": "10652475392030.jpg",
            "categories": [
                {
                    "id": 8,
                    "imageName": "DSC_0964__67640.1499289859.webp",
                    "categoryName": "Action cameras"
                }
            ]
        },
        "quantity": 1
    },
    {
        "id": 13,
        "product": {
            "id": 13,
            "title": "SONY HDR-PJ410B",
            "description": "Resolution : 9.2 MPx\r\nType : FLASH MEMORY\r\nDisplay in inch : 2.7 \"\r\nDigital approach : x350",
            "price": 250,
            "mainImageName": "9015824646174.jpg",
            "categories": [
                {
                    "id": 7,
                    "imageName": "EOS-M10_001.png",
                    "categoryName": "Cameras"
                }
            ]
        },
        "quantity": 3
    }
    ...
]
```

--------------------------------------------------

### Add order item to shopping cart
URL: `/order/items`\
Method: `POST`\
Required parameters: `product.id`\
Optional parameters: `quantity`

#### Request body (JSON):
```JavaScript
{
    "product": {
        "id": 2
    },
    "quantity": 4
}
```

#### Response:
```JavaScript
{
    "message": "Product was successful added to your shopping cart",
    "error": false
}
```

--------------------------------------------------

### Delete order item from shopping cart
URL: `/order/items/{id}`\
Method: `DELETE`

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```

--------------------------------------------------

### Update order item quantity
URL: `/order/items/{id}`\
Method: `PUT`\
Required parameters: `quantity`

#### Request:
PUT /order/items/13?quantity=4

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```

--------------------------------------------------

### Buy products from the shopping cart
URL: `/order/buy`\
Method: `POST`

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```

--------------------------------------------------

### Create a new product
URL: `/admin/products/create`\
Method: `POST`\
Required parameters: `tile, description, price, productCategories(ids of the categories that this product will be in)`\
Optional parameters: `image(file)`

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```

--------------------------------------------------

### Update existing product
URL: `/admin/products/update`\
Method: `POST`\
Required parameters: `tile, description, price, productCategories(ids of the categories that this product will be in)`\
Optional parameters: `image(file)`

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```

--------------------------------------------------

### Delete product by id
URL: `/admin/products/{id}`\
Method: `DELETE`

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```

--------------------------------------------------

### Create new category
URL: `/admin/categories/create`\
Method: `POST`\
Required parameters: `categoryName`\
Optional parameters: `image(file)`

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```

--------------------------------------------------

### Update existing category
URL: `/admin/categories/update`\
Method: `POST`\
Required parameters: `id, categoryName`\
Optional parameters: `image(file)`

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```

--------------------------------------------------

### Delete existing category
URL: `/admin/categories/{id}`\
Method: `DELETE`

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```

--------------------------------------------------

### Get orders by status
URL: `/admin/orders`\
Method: `GET`\
Required parameters: `status`

Parameter values `status`:
- `sent`
- `completed`

#### Response:
```JavaScript
[
    {
        "id": 2,
        "status": "sent",
        "fullName": "Test Test",
        "price": 2484,
        "email": "test@abv.bg"
    },
    {
        "id": 5,
        "status": "sent",
        "fullName": "Test Test",
        "price": 2600,
        "email": "test@abv.bg"
    }
]
```

--------------------------------------------------

### Change order status to completed
URL: `/admin/orders/complete/{id}`\
Method: `POST`

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```

--------------------------------------------------

### Change order status to sent
URL: `/admin/orders/complete/{id}`\
Method: `PUT`

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```

--------------------------------------------------

### Get user by order id
URL: `/admin/orders/{id}/user`\
Method: `GET`

#### Response:
```JavaScript
{
    "id": 3,
    "username": "john.doe",
    "country": "USA",
    "address": "st. John Doe 2",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+123456789",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ]
}
```

--------------------------------------------------

### Get items by order id
URL: `/admin/orders/{id}/items`\
Method: `GET`

#### Response:
```JavaScript
[
    {
        "id": 8,
        "product": {
            "id": 15,
            "title": "Canon",
            "description": "Description here! Edited!",
            "price": 450,
            "mainImageName": "EOS-M10_0011.png",
            "categories": [
                {
                    "id": 7,
                    "imageName": "EOS-M10_001.png",
                    "categoryName": "Cameras"
                }
            ]
        },
        "quantity": 3
    },
    {
        "id": 9,
        "product": {
            "id": 14,
            "title": "XMART WF430 4K",
            "description": "",
            "price": 128,
            "mainImageName": "10652475392030.jpg",
            "categories": [
                {
                    "id": 8,
                    "imageName": "DSC_0964__67640.1499289859.webp",
                    "categoryName": "Action cameras"
                }
            ]
        },
        "quantity": 3
    },
    ...
]
```
