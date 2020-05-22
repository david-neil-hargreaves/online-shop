
package sainsburys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.util.exception.ServiceException;

@Service
public class JsonProductReportFormatterImpl implements ProductReportFormatter {

  private static final Logger LOGGER = LogManager.getLogger(JsonProductReportFormatterImpl.class);

  @Override
  public String formatReport(ProductReportContents productReportContents) throws ServiceException {
    ObjectMapper objectMapper = new ObjectMapper();
    String formattedProductReport;
    try {
      DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter();
      defaultPrettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
      objectMapper.setDefaultPrettyPrinter(defaultPrettyPrinter);
      formattedProductReport =
          objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productReportContents);
    } catch (JsonProcessingException exception) {
      LOGGER.error(exception.getMessage(), exception);
      throw new ServiceException(exception);
    }
    return formattedProductReport;
  }

}
