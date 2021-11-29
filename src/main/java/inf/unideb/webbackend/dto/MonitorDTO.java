package inf.unideb.webbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorDTO {

    private Long id;

    private String name;

    private BrandDTO brand;

    private String aspectRatio;

    private Integer refreshRate;

    private String resolution;

    private String panelType;

    private Integer displaySize;
}
