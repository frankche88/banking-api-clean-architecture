# Banking API - Clean Architecture
Banking API - Clean Architecture
Prueba
Pendientes: resumen de boletas
Resumen de anulados

PDF: templates de JASPER configurables
datos de la autenticacion desde el cliente que llama
Tabla de usuarios y clave SOL



JSON de ejemplo:
20512691529-01-F2IT-2.json
EN el JKS se debe de registar un certificado con el ruc 20512691529
y en la tabbla debe de existir un registro con el ruc: 20512691529


FacturadorKey.jks


drop table CERTI;
CREATE TABLE `CERTI` (
  `idcerti` int(11) NOT NULL,
  `idempresacli` int(11) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `alias` varchar(20) DEFAULT NULL,
  `fecini` date DEFAULT NULL,
  `fecfin` date DEFAULT NULL,
  `estado` char(1) DEFAULT NULL
);

INSERT INTO CERTI
(idcerti, idempresacli, alias, password, fecini, fecfin, estado)
VALUES(1, 1, '20512691529', 12345678, '2018-09-04', '2028-09-04', '1');


INSERT INTO CERTI
(idcerti, idempresacli, alias, password, fecini, fecfin, estado)
VALUES(1, 12, '20512691529', 12345678, '2018-09-04', '2028-09-04', '1');

select * from CERTI;


curl -H "Content-type: application/json"  http://localhost:8090/v1/users/_login -d '{"nomusuario":"admin", "password":"admin"}'





prueba generar XML y PDF
curl -H "Content-Type: application/json" \
    -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
    -X POST http://localhost:9001/gen/GenerarXmlCpe/01/20512691529-01-F2IT-2 \
    -d @20512691529-01-F2IT-2.json -ss; echo

Enviar XML hacia SUNAT
curl -H "Content-Type: application/json" \
     -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
     -X POST http://localhost:9001/gen/SendXmlCpe/20512691529-01-F2IT-2 -ss; echo


Subir certificado
curl \
  -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
  -F "password=1234" \
  -F "numRuc=20131312955" \
  -F "cerFile=@sunat-ple-cert1.pfx" \
  http://localhost:9001/gen/certificado
  
  
# leer usuarios sol
curl -H "Content-Type: application/json" \
  -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
  -X GET http://localhost:9001/gen/20131312955/usuario-sol -ss ; echo

# crear usuario sol
curl -H "Content-Type: application/json" \
  -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
  -X POST http://localhost:9001/gen/20131312955/usuario-sol \
  -d '{"idususol":2,"idempresacli":1,"usuariosol":"20512691529MODDATO1","clavesol":"moddatos","nombre":"david","apellido":"echevarria","tipodocumento":"6","nrodocumento":"20512691529"}'; echo

# eliminar usuario sol
curl -H "Content-Type: application/json" \
  -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
  -X DELETE http://localhost:9001/gen/20131312955/usuario-sol/1 

# actualizar usuario sol  
curl -H "Content-Type: application/json" \
  -H "X-Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhNGoiOiJ7XCJpZHVzdXNpc1wiOjEsXCJpZGVtcHJlc2FjbGlcIjoxLFwibnVtcnVjXCI6XCIyMDUxMjY5MTUyOVwiLFwicmF6b25cIjpcIkdSQUZJQ0EgSU5EVVNUUklBXCIsXCJub211c3VhcmlvXCI6XCJhZG1pblwiLFwicHJpdmlsZWdlc1wiOltcIlVTRVJcIl19In0=.lbmO3dsiaZFKh26TEEoOwWvhyaBEmBEmPDOyINkftq8=" \
  -X PUT http://localhost:9001/gen/20131312955/usuario-sol/6 \
  -d '{"idususol":2,"idempresacli":1,"usuariosol":"20512691529MODDATO1","clavesol":"moddatos","nombre":"david","apellido":"echevarria","tipodocumento":"6","nrodocumento":"20512691529"}'; echo





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
 
		
		
	



