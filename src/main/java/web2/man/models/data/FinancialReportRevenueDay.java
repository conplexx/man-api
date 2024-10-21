package web2.man.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.models.entities.Revenue;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinancialReportRevenueDay {
    Date day;
    double total;
    List<Revenue> revenues;
}
