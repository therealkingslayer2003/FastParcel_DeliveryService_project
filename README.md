# FastParcel_DeliveryService_project
FastParcel is a robust delivery management system that aims to automate business processes for various roles such as Delivery Manager, Branch Worker, and Driver. The system accurately simulates the real-life operations of delivery services. 

THIS IS ONLY BACKEND (SERVER) API IMPLEMENTATION


The Business Functions diagram:
![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/6e836a00-e28e-4733-828d-def423fa7f53)

The designed database:
![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/ebf07874-a492-4f4a-9677-1740ece39ce5)


As you can see, there are 3 roles in this project: Delivery Manager, Branch Worker, Driver.

Let's take a look at each role.


1. Branch Worker

   Branch Worker in the project is a person who can register new orders and change their status.


   ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/e8ced69f-e6f6-45af-a97a-822113a88956)

   Functions:
   1. Registration / Log in
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/8235413e-6413-495d-9c02-62b4dff04b2b)
   Successful registration.

      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/0e17f033-0087-4869-ab61-edde8bde8e91)
   Successful log in. We got generated JWT token.

   2. Registration a new order
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/8768d4a0-8d4d-4caa-b22b-31f049eee65a)
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/82e3c171-eed1-4826-8c14-732b0f604a04)
      Order created

   3. Changing the order status
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/00254660-147e-45a4-b815-15ea723a5276)
      Changing the order status, for example, from "ACCEPTED" to "IN_THE_WAREHOUSE".
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/74f89ab4-bc15-48b8-afa1-315f1c6cb9e7)
      The order status has been changed.


2. Driver

   ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/c4673937-c6bd-4393-b2e4-e1db68f33ba0)


   Functions:
   1. Registration / Log in
![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/42b55818-566c-4229-a468-9c525c2e52d5)
      Registration was done like Employee's one. Successful login.
   2. Monitoring the assigned shipments
         ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/82ff0308-49c8-4bdb-abd6-14fdba6e12e0)

   3. Changing the shipment status
       ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/a39050b9-5e74-4172-8d45-cb6433c8010e)
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/59a1b0ec-fb2b-48f2-868c-f7aeddc57f1b)
            Driver can change the shipment status (and all the related orders) from "FORMED" to "IN_TRANSIT".
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/9b844d60-ca98-46d8-81f9-fe7f7441cb27)


  
3. Delivery Manager

   Delivery Manager in the project is a person who can monitor the order and shipment status history, create and assign shipments to drivers.

   
   ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/5fc1d114-0aef-483a-a8f1-468d4f338eed)



   Functions:
   1. Registration / Log in
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/3d3645a6-87e8-422e-858b-5bec9f238be7)
      Registration was done like Employee's one. Successful login.
   2. Shipment tracking
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/561b8ba7-9d71-4105-b670-ea31830eae23)
      Delivery Manager can type any shipment id and get the information about it.
   3. Shipment formation
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/1bde7413-ef45-4885-9ef0-e178f99e46e5)
      ![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/cbeee56a-f6e0-4759-b501-08705cf70791)
      The new shipment has been successfully created.



![image](https://github.com/therealkingslayer2003/FastParcel_DeliveryService_project/assets/93054726/c838feab-40cb-4734-b82f-2d9a038e4a36)
OpenAPI + Swagger UI support.
