package web2.man.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinancialReportEquipmentCategoryInfo {
    String equipmentCategoryName;
    double totalRevenue;
}