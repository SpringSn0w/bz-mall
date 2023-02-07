import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TestUpload {

    @Test
    public void testUpload() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, FileNotFoundException {
        MinioClient client = MinioClient.builder()
                .endpoint("http://192.168.10.12:9090")
                .credentials("agg", "agg11111")
                .build();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\15241\\Desktop\\photo\\IMG_20210118_011330_118.jpg");

        try {
            ObjectWriteResponse response = client.putObject(PutObjectArgs.builder()
                    .bucket("test")
                    .object("mm.jpg")
                    .stream(inputStream,inputStream.available(),-1)
                    .build());

            System.out.println("response = " + response);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
