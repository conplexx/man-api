package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import web2.man.models.data.FinancialReportEquipmentCategoryInfo;

import java.util.List;

@AllArgsConstructor
@Data
public class FinancialReportEquipmentCategoryResponse {
    double total;
    List<FinancialReportEquipmentCategoryInfo> reports;
}
