# E-Commerce-Shop-Back-End

Spring REST service for e commerce shop. The shop have two type of users admins and normal users. Admins can create, edit or delete categories and products. They can check sent orders by users and to complete them. Users have shopping cart. They can search products by categories or by keywords and to order them. All the products are paginated.

## Technologies used:

Spring MVC, Spring Boot, Spring Data, Spring Security, JSON, MySQL, JWT(Json Web Token).

## REST Documentation for User with role ROLE_USER:

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

### GET /user -> Get authenticated user's information.
Required -> Token from login in Authorization header.

#### Response:
```JavaScript
{
    "id": 3,
    "username": "test",
    "country": "Bg",
    "address": "asd 123",
    "email": "test@abv.bg",
    "firstName": "Test",
    "lastName": "Test",
    "phone": "08900323",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ]
}
```
<br/>

### GET /products -> Get products ( by searchWord/categoryId,page OPTIONAL).
Not required params -> searchWord, categoryId, page.

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
    },
    {
        "id": 12,
        "title": "PANASONIC HC-V160EP-K",
        "description": "",
        "price": 160,
        "mainImageName": "8977509351454.jpg",
        "categories": [
            {
                "id": 7,
                "imageName": "EOS-M10_001.png",
                "categoryName": "Cameras"
            }
        ]
    },
    {
        "id": 11,
        "title": "LENOVO YOGA ZA0V0210BG",
        "description": "",
        "price": 700,
        "mainImageName": "9835454103582.jpg",
        "categories": [
            {
                "id": 2,
                "imageName": "yoga-900-header-hero.png",
                "categoryName": "Laptops"
            }
        ]
    },
    {
        "id": 10,
        "title": "DELL INSPIRON 3567 /994281",
        "description": "",
        "price": 550,
        "mainImageName": "10083868377118.jpg",
        "categories": [
            {
                "id": 2,
                "imageName": "yoga-900-header-hero.png",
                "categoryName": "Laptops"
            }
        ]
    },
    {
        "id": 9,
        "title": "ASUS T300FA-FE004H",
        "description": "",
        "price": 350,
        "mainImageName": "9507688480798.jpg",
        "categories": [
            {
                "id": 2,
                "imageName": "yoga-900-header-hero.png",
                "categoryName": "Laptops"
            }
        ]
    },
    {
        "id": 8,
        "title": "ACER ASPIRE ES1-132-C81F /RED",
        "description": "",
        "price": 250,
        "mainImageName": "10385245536286.jpg",
        "categories": [
            {
                "id": 2,
                "imageName": "yoga-900-header-hero.png",
                "categoryName": "Laptops"
            }
        ]
    },
    {
        "id": 7,
        "title": "NOKIA 8 BLUE",
        "description": "?????? ?? ?????? ? INCH : 5.3 \"\r\n?????????? ?? ??????? : IPS CAPACITIVE TOUCHSCREEN\r\n???????? : 2.5GHz Quad+1.8 Quad Core\r\n???? ?? ????? ? ?????\r\n????? ?????? : 13.0 MPx\r\nBLUETOOTH\r\n4G",
        "price": 400,
        "mainImageName": "10504947564574.jpg",
        "categories": [
            {
                "id": 1,
                "imageName": "PCD-Filtertype-Note8-dsb.png",
                "categoryName": "Smartphones"
            }
        ]
    },
    {
        "id": 6,
        "title": "APPLE IPHONE 7 32GB GOLD",
        "description": "?????? ?? ?????? ? INCH : 4.7 \"\r\n?????????? ?? ??????? : RETINA HD 3D TOUCHSCREEN\r\n???????? : APPLE A10 64-bit\r\n????? ?????? : 12.0 MPx\r\nBLUETOOTH\r\n4G",
        "price": 920,
        "mainImageName": "9754986151966.jpg",
        "categories": [
            {
                "id": 1,
                "imageName": "PCD-Filtertype-Note8-dsb.png",
                "categoryName": "Smartphones"
            }
        ]
    },
    {
        "id": 5,
        "title": "APPLE IPHONE X 256GB SPACE GRAY",
        "description": "??????????? ?????\r\n?????? ? ??????\r\n??????\r\n????? ?? ???????\r\n?????? ?? ?????? ? INCH : 5.8 \"\r\n?????????? ?? ??????? : OLED Super Retina\r\n???????? : A11 64-Bit\r\n????? ?????? : 12.0 MPx\r\nBLUETOOTH\r\n4G",
        "price": 1850,
        "mainImageName": "10411580063774.jpg",
        "categories": [
            {
                "id": 1,
                "imageName": "PCD-Filtertype-Note8-dsb.png",
                "categoryName": "Smartphones"
            }
        ]
    },
    {
        "id": 4,
        "title": "APPLE IPHONE 8 256GB GOLD",
        "description": "?????? ?? ?????? ? INCH : 4.7 \"\r\n?????????? ?? ??????? : Retina HD\r\n???????? : A11 64-Bit\r\n????? ?????? : 12.0 MPx\r\nBLUETOOTH\r\n4G",
        "price": 900,
        "mainImageName": "10359757864990.jpg",
        "categories": [
            {
                "id": 1,
                "imageName": "PCD-Filtertype-Note8-dsb.png",
                "categoryName": "Smartphones"
            }
        ]
    }
]
```
<br/>

### GET /products/{id} -> Get product by id.
Required param -> id.

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
<br/>

### GET /products/count -> Get products count.
Not required params -> searchWord, categoryId

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
<br/>

### GET /categories -> Get all categories.

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
    {
        "id": 5,
        "imageName": "lenovo-desktop-legion-y720-tower-feature-2.png",
        "categoryName": "Desktops"
    },
    {
        "id": 6,
        "imageName": "tablets-repair.png",
        "categoryName": "Tablets"
    },
    {
        "id": 7,
        "imageName": "EOS-M10_001.png",
        "categoryName": "Cameras"
    },
    {
        "id": 8,
        "imageName": "DSC_0964__67640.1499289859.webp",
        "categoryName": "Action cameras"
    },
    {
        "id": 10,
        "imageName": "107894522511661.jpg",
        "categoryName": "MicroSD Edited!"
    }
]
```
<br/>

### GET /categories/{id} -> Get category by id.

#### Response:
```JavaScript
{
    "id": 1,
    "imageName": "PCD-Filtertype-Note8-dsb.png",
    "categoryName": "Smartphones"
}
```
<br/>

### GET /order/items/count -> Count of the order items in shopping cart of authenticated user.

#### Response:
```JavaScript
{
    "count": 4
}
```
<br/>

### GET /order/items -> Get order items in the shopping cart of authenticated user.

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
]
```
<br/>

### POST /order/items -> Add order item to shopping cart.
Required param -> product -> id.
Not required param -> quantity.

#### Request (JSON)
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
<br/>

### DELETE /order/items/{id} -> Delete order item from shopping cart.
Required param -> id (id of the order item).

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```
<br/>

### PUT /order/items/{id} -> Update order item quantity.
Required param -> id (id of the order item), quantity.

#### Example
PUT /order/items/13?quantity=4

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```
<br/>

### POST /order/buy -> Buy products from the shopping cart.

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```
<br/>

## REST Documentation for User with role ROLE_ADMIN:

### POST /admin/products/create -> Create new product.
Required param -> tile, description, price, productCategories(id of the category that this product will be in).
Not required param -> image(file).

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```
<br/>

## REST Documentation for User with role ROLE_ADMIN:

### POST /admin/products/update -> Update existing product.
Required param -> tile, description, price, productCategories(id of the category that this product will be in).
Not required param -> image(file).

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```
<br/>

### DELETE /admin/products/{id} -> Delete existing product by id.
Required param -> id (id of the product).

#### Response:
```JavaScript
{
    "message": null,
    "error": false
}
```
<br/>

### GET /admin/orders -> Get orders by status.
Required param -> status ( sent / completed ).

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
<br/>

### POST /admin/orders/complete/{id} -> Change order status to completed.
Required param -> id.

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```
<br/>

### PUT /admin/orders/complete/{id} -> Change order status to sent.
Required param -> id.

#### Response:
```JavaScript
{
    "message": "successful",
    "error": false
}
```
<br/>

### GET /admin/orders/{id}/user -> Extract user from order.
Required param -> id.

#### Response:
```JavaScript
{
    "id": 3,
    "username": "test",
    "country": "Bg",
    "address": "asd 123",
    "email": "test@abv.bg",
    "firstName": "Test",
    "lastName": "Test",
    "phone": "08900323",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ]
}
```
<br/>

### GET /admin/orders/{id}/items -> Extract items from order.
Required param -> id.

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
    {
        "id": 10,
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
]
```
<br/>


