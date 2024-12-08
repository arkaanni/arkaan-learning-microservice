use actix_web::{get, web, App, HttpResponse, HttpServer, Responder};

#[get("/echo")]
async fn hello() -> impl Responder {
    HttpResponse::Ok().body("Hello world!")
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(
                web::scope("auth")
                    .service(hello)
            )
    })
        .bind(("127.0.0.1", 8443))?
        .run()
        .await
}
