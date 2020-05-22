
package sainsburys.service;

import org.springframework.stereotype.Service;

@Service
public class ConsoleReportOutputterImpl implements ReportOutputter {

  @Override
  public void outputReport(String report) {
    System.out.println(report);
  }

}
