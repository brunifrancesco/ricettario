package controllers

import scala.concurrent._
import duration._
import org.specs2.mutable._

import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._
import java.util.concurrent.TimeUnit


/**
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class UsersIT extends Specification {

  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)

  "Recipes" should {

    "insert a valid json" in {
      running(FakeApplication()) {
        val request = FakeRequest.apply(POST, "/recipe").withJsonBody(Json.obj(
          "name" -> "RecipeName",
          "ingredients" -> "Ingredients",
          "procedure" -> "Procedure",
          "category" -> "Category"))
        val response = route(request)
        response.isDefined mustEqual true
        val result = Await.result(response.get, timeout)
        result.header.status must equalTo(CREATED)
      }
    }

    "fail inserting a non valid json" in {
      running(FakeApplication()) {
        val request = FakeRequest.apply(POST, "/recipe").withJsonBody(Json.obj(
          "name" -> "RecipeName"
          ))
        val response = route(request)
        response.isDefined mustEqual true
        val result = Await.result(response.get, timeout)
        result.header.status mustEqual BAD_REQUEST
      }
    }
  }
}