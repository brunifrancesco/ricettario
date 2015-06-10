package controllers

import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.Future
import reactivemongo.api.Cursor
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import org.slf4j.{LoggerFactory, Logger}
import javax.inject.Singleton
import play.api.mvc._
import play.api.libs.json._
import models._
import models.JsonFormats._
import reactivemongo.bson._



/**
 * The Users controllers encapsulates the Rest endpoints and the interaction with the MongoDB, via ReactiveMongo
 * play plugin. This provides a non-blocking driver for mongoDB as well as some useful additions for handling JSon.
 * @see https://github.com/ReactiveMongo/Play-ReactiveMongo
 */
@Singleton
class Recipes extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Recipes])

  /*
   * Get a JSONCollection (a Collection implementation that is designed to work
   * with JsObject, Reads and Writes.)
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the collection reference to avoid potential problems in development with
   * Play hot-reloading.
   */
  def collection: JSONCollection = db.collection[JSONCollection]("recipes")

  // ------------------------------------------ //
  // Using case classes + Json Writes and Reads //
  // ------------------------------------------ //


  def createRecipe = Action.async(parse.json) {
    request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
      request.body.validate[Recipe].map {
        recipe =>
        // `user` is an instance of the case class `models.User`
          collection.insert(recipe).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"Recipe Created")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }


  def updateRicetta() = Action.async(parse.json) {
    request =>
      request.body.validate[Recipe].map {
        recipe =>
          // find our user by first name and last name
          val nameSelector = Json.obj("name" -> recipe.name)
          collection.update(nameSelector, recipe).map {
            lastError =>
              logger.debug(s"Successfully updated with LastError: $lastError")
              Created(s"User Updated")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
  
  def deleteRicetta(name: String) = Action.async {
    val nameSelector = Json.obj("name" -> name)
    collection.remove(nameSelector).map{
      lastError =>
         Ok("Deleted")
    }
  }

  def findRecipes = Action.async {
    // let's do our query
    val cursor: Cursor[Recipe] = collection.
      // find all
      find(Json.obj(
        "$query" -> Json.obj()))
      // perform the query and get a cursor of JsObject
      .cursor[Recipe]

    // gather all the JsObjects in a list
    val futureUsersList: Future[List[Recipe]] = cursor.collect[List]()

    // transform the list into a JsArray
    val futurePersonsJsonArray: Future[JsArray] = futureUsersList.map { recipes =>
      Json.arr(recipes)
    }
    // everything's ok! Let's reply with the array
    futurePersonsJsonArray.map {
      users =>
        Ok(users(0))
    }
  }

}
