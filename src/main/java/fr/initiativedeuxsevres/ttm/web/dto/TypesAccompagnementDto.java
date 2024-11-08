package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;

public enum TypesAccompagnementDto {
    RESSOURCES_HUMAINES,
    FINANCE_COMPTABILITE,
    JURIDIQUE,
    INFORMATIQUE,
    COMMERCIAL_COMMUNICATION;

    public static TypesAccompagnementDto mapTypesAccompagnementToTypesAccompagnementDto(TypesAccompagnement typesAccompagnement) {
        return switch (typesAccompagnement) {
            case RESSOURCES_HUMAINES -> TypesAccompagnementDto.RESSOURCES_HUMAINES;
            case FINANCE_COMPTABILITE -> TypesAccompagnementDto.FINANCE_COMPTABILITE;
            case JURIDIQUE -> TypesAccompagnementDto.JURIDIQUE;
            case INFORMATIQUE -> TypesAccompagnementDto.INFORMATIQUE;
            case COMMERCIAL_COMMUNICATION -> TypesAccompagnementDto.COMMERCIAL_COMMUNICATION;
        };
    }
}
