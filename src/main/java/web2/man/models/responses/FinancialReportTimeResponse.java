package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.models.data.FinancialReportRevenueDay;

import java.util.List;

@Data
@AllArgsConstructor
public class FinancialReportTimeResponse {
    Double total;
    List<FinancialReportRevenueDay> days;
}
