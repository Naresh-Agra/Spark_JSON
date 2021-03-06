package batters
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.jdbc._
import scala.io.Source
import org.apache.spark.sql.functions.explode
import org.apache.spark.sql.functions._


object batters {
  
  def main(args:Array[String]):Unit = {
    
        val conf=new SparkConf().setAppName("Batters_Task").setMaster("local[*]").set("spark.driver.allowMultipleContexts","true")
        val sc=new SparkContext(conf)
        sc.setLogLevel("ERROR")
    
        val spark=SparkSession
              .builder()
              .config(conf)
              .getOrCreate()
              
          import spark.implicits._
    
        val data=spark.read.option("multiLine",true).format("json").load("file:///E://Workouts//Data//blueArrayJson.txt")
        data.printSchema()
        println("Class Task-Batters")
        val output=data.withColumn("batters_batter",explode($"batters.batter")).withColumn("topping",explode($"topping")).selectExpr("batters_batter.id as Batters_ID","batters_batter.type as Batters_Type","id","name","ppu","topping.id as Topping_Id","topping.type as Topping_Type","type")
        output.show(false)
    
    
    
  }
  
}