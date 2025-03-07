import org.junit.Assert;
import org.junit.Test;
import ru.ilya.http.server.domain.HTTPResponseStatusCode;
import ru.ilya.http.server.domain.Response;
import ru.ilya.http.server.service.FileService;
import ru.ilya.http.server.service.ResponseSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TestResponseSerializer {

    @Test
    public void responseSerialize() throws IOException {
        HashMap<String, String> header = new HashMap<>();
        FileService fileService = new FileService(Path.of("D:\\Java\\FilesSimpleServerHTPP"));
        HTTPResponseStatusCode httpResponseStatusCode = HTTPResponseStatusCode.RESPONSE_CODE_200;

        InputStream inputStream = fileService.readFile(Path.of("image.html"));

        //header.put("HTTP/1.1", httpResponseStatusCode.getName());
        header.put("Content-Type:", "text/html; charset=utf-8");
        String body = new String(inputStream.readAllBytes());

        Response response = new Response(httpResponseStatusCode, header, body);

        ResponseSerializer responseSerializer = new ResponseSerializer();
        String stringResponse = responseSerializer.serialize(response);

        String testResponse = """
                HTTP/1.1 200 Ok\r
                Content-Type: text/html; charset=utf-8\r
                \r
                <!DOCTYPE html>\r
                <html>\r
                    <head>\r
                        <title>Картинка</title>\r
                    </head>\r
                    <body>\r
                        <h1>Страница с картинкой</h1>\r
                        <img src="/image.jpg" alt="Картинка">\r
                    </body>\r
                </html>""";
        Assert.assertEquals(testResponse, stringResponse);
    }

}
