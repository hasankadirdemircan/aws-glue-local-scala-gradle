import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.util.{GlueArgParser, Job}
import org.apache.spark.SparkContext

import java.sql.DriverManager
import scala.collection.JavaConverters._

object Connection {
  def main(sysArgs: Array[String]) {
    val spark: SparkContext = new SparkContext()
    val glueContext: GlueContext = new GlueContext(spark)

    val args = GlueArgParser.getResolvedOptions(sysArgs, Seq("TempDir", "JOB_NAME").toArray)
    Job.init(args("JOB_NAME"), glueContext, args.asJava)

    classOf[org.postgresql.Driver]
    val connection = "jdbc:..."
    val conn = DriverManager.getConnection(connection)

    try {
      val stm = conn.createStatement()
      stm.executeUpdate("sql here.")

    } finally {
      conn.close()
    }
    Job.commit()
  }
}
