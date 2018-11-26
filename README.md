# Banking API - Clean Architecture
Banking API - Clean Architecture
.
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjEiLCJ1c2VySWQiOiIxIiwic3ViIjoiYWRtaW4iLCJqdGkiOiIwNDNiNWEzMC02ZTRiLTQ3NDctODgzOS0wNjRmZTViZGE5ODUiLCJlbWFpbCI6ImFkbWluQGhlbnJ5Z3VzdGF2by5jb20iLCJyb2xlIjoiYWRtaW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbiIsImV4cCI6MTUzOTMwNjY4MiwiaXNzIjoiaHR0cHM6Ly9zaG9wcGluZ2NhcnQuY29tIiwiYXVkIjoiaHR0cHM6Ly9zaG9wcGluZ2NhcnQuY29tIn0.OrqUMcE05E97aWlvWPgzWuiAX8DgDpTtgU-MLcxy-ek"









JWT
setx JWT_TOKEN_KEY "SUPERPASSWORD123"

setx JWT_ISSUER "https://shoppingcart.com"

setx JWT_AUDIENCE "https://shoppingcart.com"

SecurityAlgorithms.HmacSha256

URL: https://shoppingcartidentity.cfapps.io/api/auth/login
swagger : https://shoppingcartidentity.cfapps.io/swagger/index.html

POST: 

 public class LoginDto
    {
        public string Email { get; set; }
     
        public string Password { get; set; }

        public bool RememberMe { get; set; }
    }
	
RESPONSE:

login Response: 

{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjEiLCJ1c2VySWQiOiIxIiwic3ViIjoiYWRtaW4iLCJqdGkiOiIwNDNiNWEzMC02ZTRiLTQ3NDctODgzOS0wNjRmZTViZGE5ODUiLCJlbWFpbCI6ImFkbWluQGhlbnJ5Z3VzdGF2by5jb20iLCJyb2xlIjoiYWRtaW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbiIsImV4cCI6MTUzOTMwNjY4MiwiaXNzIjoiaHR0cHM6Ly9zaG9wcGluZ2NhcnQuY29tIiwiYXVkIjoiaHR0cHM6Ly9zaG9wcGluZ2NhcnQuY29tIn0.OrqUMcE05E97aWlvWPgzWuiAX8DgDpTtgU-MLcxy-ek"
}

-----------------------------------------------------
Role: admin
email: admin@henrygustavo.com
passowrd: P@$$w0rd


Role: member
Email: member@test.com
password: P@$$w0rd
-------------------------------------------------
Get: api/orders

 Devuelve una lista de 
 
  public class OrderHeaderOutput
    {
        public int Id { get; set; }
        public string FullName { get; set; }
        public string Address { get; set; }
        public string OrderDate { get; set; }
        public decimal Total { get; set; }
        public string Currency { get; set; }
    }

Get: api/orders/{id}

devuelve un objecto 

 public class OrderOutputDto
    {
        public int Id { get; set; }
        public string  FullName { get; set; }
        public string Address { get; set; }
        public string OrderDate { get; set; }
        public decimal  Total { get; set; }
        public string Currency { get; set; }

        public List <OrderItemOutputDto>  OrderItems { get; set; }
    }

public class OrderItemOutputDto
    {
        public int ProductId { get; set; }
        public string  ProductName { get; set; }
        public string  PictureUrl { get; set; }
        public decimal Unit { get; set; }
        public decimal UnitPrice { get; set; }
        public decimal Total { get; set; }
        public string  Currency { get; set; }
    }
	

	
Post: api/orders

 recibe un objecto
 
 
  public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Address { get; set; }
        public string CreditCardNumber { get; set; }
        public string CreditCardAuthCode { get; set; }
        public List<OrderItemInputDto> OrderItems { get; set; }
		
 public class OrderItemInputDto
    {
        public int ProductId { get; set; }
        public string ProductName { get; set; }
        public string PictureUrl { get; set; }
        public decimal Unit { get; set; }
        public decimal UnitPrice { get; set; }
        public string Currency { get; set; }
    }

------------------------------------------------------------------------

Rabbitmq

setx RABBITMQ_URL rabbitmq://mtnnoeer:uSiU0yzxrGeuTMIcvYSMHKsw7z5G1DJH@woodpecker.rmq.cloudamqp.com/mtnnoeer
setx RABBITMQ_USERNAME mtnnoeer
setx RABBITMQ_PASSWORD uSiU0yzxrGeuTMIcvYSMHKsw7z5G1DJH


cuando la orden esta completada emite un  evento llamado : Common.Messaging.OrderCompletedEvent

Ejemplo clase:

namespace Common.Messaging
{
    public class OrderCompletedEvent
    {
        public string BuyerId { get; set; }
        public OrderCompletedEvent(string buyerId)
        {
            BuyerId = buyerId;
        }
    }
}

-----------------------------------------------------------------------------
 
		
		HTTP Method: POST
URL: https://fcm.googleapis.com/fcm/send
Header:

Content-Type:	application/json
Authorization:	key=AIzaSyBn6w8KiDpB6Fm3UPq4qzVjewjhrurRqZ0

Body:

{
"notification": {
  "title": "New order", 
  "body": "A new order was created:" + idOrder
 },
 "to" : "dO7j0zRnQWw:APA91bFTSHJd0l1GNcsuQ3yplRqMRWM7C_nC431OFn1Dvo6ee4xUwzYyl8cBw18X6qvegA35eaN1tR83y28Z_zbbJP0Zo3F_oy0JwsqC34QTZlP_WyGtsma00hHa8JNuSg1oawlUb7bS"
}
	



