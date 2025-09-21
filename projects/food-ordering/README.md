## **🎯 Objective**

Design a system similar to **Swiggy/Zomato** where customers can order food from restaurants. The case study explains how **different Java concepts** would be applied in building this project.

---

## **🔹 1\. Java Fundamentals**

- Create core entities: **Customer, Restaurant, MenuItem, Order**.

- Each class has attributes (id, name, price, etc.) and methods (placeOrder, calculateBill).

- Concepts like **constructors, methods, objects, encapsulation, toString()** are used.

---

## **🔹 2\. Inheritance & Polymorphism**

- `Customer` → `RegularCustomer` and `PremiumCustomer` (Premium customers get discounts).

- `Order` → `PickupOrder` and `DeliveryOrder` (different processing).

- Method **overriding** ensures different behavior for each type.

---

## **🔹 3\. Exception Handling**

- Custom exceptions:

  - `RestaurantNotFoundException` (if user searches for an unavailable restaurant).

  - `PaymentFailedException` (if payment gateway fails).

- Exception handling ensures system doesn’t crash, instead shows user-friendly error messages.

---

## **🔹 4\. Threads**

- Multiple customers can place orders **simultaneously**.

- Separate threads handle:

  - Processing orders

  - Sending notifications

  - Updating delivery status

- For example, when 100 customers place orders at the same time, threads ensure smooth processing without delays.

---

## **🔹 5\. Collections Framework**

- Store data in suitable collections:

  - **HashMap** → Restaurants and their menus

  - **ArrayList** → List of all orders

  - **TreeSet** → Sorted list of top-rated restaurants

- Collections help manage large amounts of data efficiently.

---

## **🔹 6\. JDBC**

- Connect system to a **MySQL database**:

  - `customers` table (id, name, email)

  - `restaurants` table (id, name, rating)

  - `orders` table (orderId, customerId, amount, status)

- JDBC ensures data persistence (so orders aren’t lost if the system restarts).

---
