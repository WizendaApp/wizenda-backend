package ao.wizenda.backend.controllers;

import ao.wizenda.backend.controllers.docs.PartnerControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PartnerController implements PartnerControllerDocs {
}
