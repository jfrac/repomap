package tfg.repomap;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import tfg.repomap.mapping.Mapping;

public class SchemaGenerator {
	public static void main(String[] args) throws IOException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Mapping.class);
		SchemaOutputResolver sor = new MySchemaOutputResolver();
		jaxbContext.generateSchema(sor);
	}
}

class MySchemaOutputResolver extends SchemaOutputResolver {

    public Result createOutput(String namespaceURI, String suggestedFileName) 
		throws IOException {
        File file = new File(suggestedFileName);
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        return result;
    }

}