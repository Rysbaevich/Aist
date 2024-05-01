package kg.rysbaevich.aist.model.dto.customer;


import kg.rysbaevich.aist.model.entity.customer.Image;

public record ImageDto(String customerId, byte[] photo) {
    public ImageDto(Image image) {
        this(image.getCustomer().getId(), image.getPhoto());
    }
}
