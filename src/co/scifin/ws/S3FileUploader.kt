package co.scifin.ws

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.errors.MinioException
import java.io.IOException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

object S3FileUploader
{
    @Throws(NoSuchAlgorithmException::class, IOException::class, InvalidKeyException::class)
    @JvmStatic
    fun main(args: Array<String>)
    {
        try
        {
            // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
            val minioClient = MinioClient("http://127.0.0.1:9000", "minioadmin", "minioadmin")

            // Check if the bucket already exists.
            val isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket("scifin").build())
            if (isExist)
            {
                println("Bucket already exists.")
            }
            else
            {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("scifin").build())
            }

            // Upload the zip file to the bucket with putObject
            minioClient.putObject("scifin", "cities_scifin.parquet", "/Users/wellytambunan/Repos/kotlin-scifin/resources/cities.parquet", null)
            println("cities_scifin.parquet is successfully uploaded")
        }
        catch (e: MinioException)
        {
            println("Error occurred: $e")
        }
    }
}