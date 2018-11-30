package app.ccb.config.common.parsers;


import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Component
public class JaxbImpl implements JaxbParser {

    private JAXBContext context;
    private Unmarshaller unmarshaller;
    private BufferedReader bfr;



    @Override
    public <T> T toString(String resourcePath, Class<T> klass) throws JAXBException {
        context = JAXBContext.newInstance(klass);
        unmarshaller = context.createUnmarshaller();
        return  (T) unmarshaller.unmarshal(new File(resourcePath));
    }

    @Override
    public void fromString(Object object, BufferedWriter bfw) {

    }


}
