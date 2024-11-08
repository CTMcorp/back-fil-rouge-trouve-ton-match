package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;

public enum SecteursActivitesDto {
    SERVICES_ADMINISTRATIFS_SOUTIEN("Services administratifs et soutien"),
    ACTIVITES_TECHNIQUES_SCIENTIFIQUES("Activités spécialisées, techniques et scientifiques"),
    AGRICULTURE("Agriculture, sylviculture et pêche"),
    ARTS_SPECTACLES("Arts, spectacles et activités récréatives"),
    COMMERCE("Commerce et réparation"),
    CONSTRUCTION_BTP("Contruction_BTP"),
    ENSEIGNEMENT("Enseignement"),
    HOTELS_RESTAURANTS("Hôtels, cafés et restaurant"),
    INDUSTRIE("Industrie"),
    INFORMATION_COMMUNICATION("Information et communication"),
    EAU_ASSAINISSEMENT_DECHETS("Eau, assainissement, gestion des déchets et dépollution"),
    ELECTRICITE_GAZ("Electricité, gaz, vapeur d'air conditionné"),
    SANTE_ACTION_SOCIALE("Santé humaine et action sociale"),
    SERVICES_AUX_ENTREPRISES("Services aux entreprises"),
    SERVICES_AUX_PARTICULIERS("Services aux particuliers"),
    TRANSPORTS("Transport");

    public final String name;
    SecteursActivitesDto(String name) {
        this.name = name;
    }

    public static SecteursActivitesDto mapSecteursActivitesToSecteursActivitesDto(SecteursActivites secteursActivites) {
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
        };
    }
}
