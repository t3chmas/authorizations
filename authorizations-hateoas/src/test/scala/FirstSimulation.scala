import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class FirstSimulation extends Simulation {

  // Configuration de l'URL de base pour les requêtes HTTP
  val httpProtocol = http
    .baseUrl("http://localhost:8080/") // URL de ton application
    .acceptHeader("application/hal+json")
    .userAgentHeader("Mozilla/5.0 Gatling Test")

  // Définition des scénarios de test
  val scn = scenario("GetPermission")
    .feed(randomStringFeeder)
    .exec(
      http("Get random permission") // Nom de la requête
        .get("/permissions/${randomString}") // Méthode GET pour la page d'accueil
        .check(status.is(200)) // Vérifie que la réponse HTTP a le code 200
    )
    .pause(1) // Pause de 5 secondes entre les requêtes

  // Configuration des utilisateurs virtuels et de leur charge
  setUp(
    scn.inject(
      atOnceUsers(10), // Injection immédiate de 10 utilisateurs
      rampUsers(50) during (10 seconds) // Ramping pour ajouter 50 utilisateurs en 10 secondes
    )
  ).protocols(httpProtocol)
}