package models

case class Recipe( name: String,
                 ingredients: String,
                 procedure: String,
                 category: String)

object JsonFormats {
  import play.api.libs.json.Json

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val recipeFormat = Json.format[Recipe]
}