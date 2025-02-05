package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;

public enum SecteursActivitesDto {
    SERVICES_ADMINISTRATIFS_SOUTIEN,
    ACTIVITES_TECHNIQUES_SCIENTIFIQUES,
    AGRICULTURE,
    ARTS_SPECTACLES,
    COMMERCE,
    CONSTRUCTION_BTP,
    ENSEIGNEMENT,
    HOTELS_RESTAURANTS,
    INDUSTRIE,
    INFORMATION_COMMUNICATION,
    EAU_ASSAINISSEMENT_DECHETS,
    ELECTRICITE_GAZ,
    SANTE_ACTION_SOCIALE,
    SERVICES_AUX_ENTREPRISES,
    SERVICES_AUX_PARTICULIERS,
    TRANSPORTS;

    public static SecteursActivitesDto mapSecteursActivitesToSecteursActivitesDto(SecteursActivites secteursActivites) {
        if (secteursActivites == null) {
            throw new IllegalArgumentException("SecteursActivites cannot be null");
        }
        return switch (secteursActivites) {
            case SERVICES_ADMINISTRATIFS_SOUTIEN -> SecteursActivitesDto.SERVICES_ADMINISTRATIFS_SOUTIEN;
            case ACTIVITES_TECHNIQUES_SCIENTIFIQUES -> SecteursActivitesDto.ACTIVITES_TECHNIQUES_SCIENTIFIQUES;
            case AGRICULTURE -> SecteursActivitesDto.AGRICULTURE;
            case ARTS_SPECTACLES -> SecteursActivitesDto.ARTS_SPECTACLES;
            case COMMERCE -> SecteursActivitesDto.COMMERCE;
            case CONSTRUCTION_BTP -> SecteursActivitesDto.CONSTRUCTION_BTP;
            case ENSEIGNEMENT -> SecteursActivitesDto.ENSEIGNEMENT;
            case HOTELS_RESTAURANTS -> SecteursActivitesDto.HOTELS_RESTAURANTS;
            case INDUSTRIE -> SecteursActivitesDto.INDUSTRIE;
            case INFORMATION_COMMUNICATION -> SecteursActivitesDto.INFORMATION_COMMUNICATION;
            case EAU_ASSAINISSEMENT_DECHETS -> SecteursActivitesDto.EAU_ASSAINISSEMENT_DECHETS;
            case ELECTRICITE_GAZ -> SecteursActivitesDto.ELECTRICITE_GAZ;
            case SANTE_ACTION_SOCIALE -> SecteursActivitesDto.SANTE_ACTION_SOCIALE;
            case SERVICES_AUX_ENTREPRISES -> SecteursActivitesDto.SERVICES_AUX_ENTREPRISES;
            case SERVICES_AUX_PARTICULIERS -> SecteursActivitesDto.SERVICES_AUX_PARTICULIERS;
            case TRANSPORTS -> SecteursActivitesDto.TRANSPORTS;
            default -> null;
        };
    }
}
