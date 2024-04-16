package uz.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.students.dto.StudentDTO;
import uz.students.dto.StudentFilterDTO;
import uz.students.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public String getProfileList(Model model,
                                 @RequestParam(value = "nameQuery", required = false) String nameQuery,
                                 @RequestParam(value = "id", required = false) String id,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        StudentFilterDTO filterDTO = correctFilterDTO(nameQuery, id);

        Page<StudentDTO> result = studentService.getProfileList(filterDTO, page, size);
        model.addAttribute("studentList", result.getContent());
        model.addAttribute("totalElements", result.getTotalElements());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("filterDTO", filterDTO);
        model.addAttribute("pageSize", 10);
        return "admin/profile/list";
    }

    private StudentFilterDTO correctFilterDTO(String nameQuery, String id) {
        StudentFilterDTO filterDTO = new StudentFilterDTO();
        /*
         * search qilinayotganda name yoki surname boyicha qidirayotganini tekshirish
         * */
        if (nameQuery == null || nameQuery.isBlank() || nameQuery.equals("null")) {
            filterDTO.setNameQuery(null);
        } else {
            filterDTO.setNameQuery(nameQuery);
        }
        /*
         * search qilinayotganda id boyicha qidirayotganini tekshirish
         * */
        if (id == null || id.isBlank() || id.equals("null")) {
            filterDTO.setId(null);
        } else {
            filterDTO.setId(id);
        }
        return filterDTO;
    }

}