package pl.jakubkozlowski.leagueoflegends.restAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderEntity {
    private Long itemPageId;
    private Long itemId;
    private Integer itemOrder;
}
