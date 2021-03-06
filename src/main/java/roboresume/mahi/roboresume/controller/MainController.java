package roboresume.mahi.roboresume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import roboresume.mahi.roboresume.models.Education;
import roboresume.mahi.roboresume.models.RoboResume;
import roboresume.mahi.roboresume.models.Skills;
import roboresume.mahi.roboresume.models.WorkExperience;
import roboresume.mahi.roboresume.repository.EducationRepository;
import roboresume.mahi.roboresume.repository.ResumeRepository;
import roboresume.mahi.roboresume.repository.SkillsRepository;
import roboresume.mahi.roboresume.repository.WorkRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
   ResumeRepository resumeRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    SkillsRepository skillsRepository;



    @GetMapping("/")
    public String Welcomepage(Model model)
    {
        String message="Welcome to Robo Resume";
        model.addAttribute("message", message);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "welcome";
    }

    @GetMapping("/addpersonalinfo")

    public String PersonalInfo(Model model)
    {
        model.addAttribute("robopersonal", new RoboResume());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return"addpersonalinfo";
    }

    @PostMapping("/addpersonalinfo")
    public String PostInfo(@Valid @ModelAttribute("robopersonal") RoboResume otherpersonal, BindingResult bindingResult,
                           Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addpersonalinfo";
        }
        resumeRepository.save(otherpersonal);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "personalresult";
    }

    @GetMapping("/addeducation")

    public String EducationInfo(Model model)
    {
        model.addAttribute("roboeducation", new Education());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return"addeducation";
    }

    @PostMapping("/addeducation")
    public String PostEducation(@Valid @ModelAttribute("roboeducation") Education othereducation, BindingResult
            bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addeducation";
        }
        educationRepository.save(othereducation);
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "educationresult";
    }
    @GetMapping("/addworkexperience")

    public String WorkInfo(Model model)
    {
       model.addAttribute("robowork", new WorkExperience());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
       return "addworkexperience";
    }
    @PostMapping("/addworkexperience")
    public String PostWork(@Valid @ModelAttribute("robowork") WorkExperience otherwork, BindingResult bindingResult,
                            Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addworkexperience";
        }
        workRepository.save(otherwork);
        System.out.println(otherwork.getEnddate());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "workresult";
    }

    @GetMapping("/addskills")

    public String SkillInfo(Model model)
    {
        model.addAttribute("roboskills", new Skills());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addskills";
    }
    @PostMapping("/addskills")
    public String PostSkill(@Valid @ModelAttribute("roboskills") Skills otherskills, BindingResult bindingResult,
              Model model)
    {
        if (bindingResult.hasErrors()) {
            return "addskills";
        }
        skillsRepository.save(otherskills);
        model.addAttribute("numberOfSkill",skillsRepository.count());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        return "skillsresult";
    }

    @GetMapping("/viewresume")

    public String PostResume(Model model)
    {
        RoboResume rbdata=resumeRepository.findOne(new Long(1));

        ArrayList<Education>educationlist=((ArrayList<Education>) educationRepository.findAll());
         rbdata.setEducations(educationlist);
       model.addAttribute("test", educationlist);

        ArrayList<WorkExperience>worklist=((ArrayList<WorkExperience>)workRepository.findAll());
        rbdata.setExperiences(worklist);

        ArrayList<Skills>skilllist=((ArrayList<Skills>)skillsRepository.findAll());
        rbdata.setNewskills(skilllist);
        model.addAttribute("robo", rbdata );
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
                return "viewresume";
    }

    @GetMapping("/test")
    public String showTable(Model model, RoboResume test)
    {
        RoboResume rbdata=resumeRepository.findOne(new Long(1));
        ArrayList<Education>educationlist=((ArrayList<Education>) educationRepository.findAll());
        rbdata.setEducations(educationlist);
        model.addAttribute("test", educationlist);

        ArrayList<WorkExperience>worklist=((ArrayList<WorkExperience>)workRepository.findAll());
        rbdata.setExperiences(worklist);

        ArrayList<Skills>skilllist=((ArrayList<Skills>)skillsRepository.findAll());
        rbdata.setNewskills(skilllist);
        model.addAttribute("robo",rbdata);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return"resumetable";
    }
    @GetMapping("/updateperson/{id}")
    public String editPerson(@PathVariable("id") long id, Model model){
        model.addAttribute("robopersonal", resumeRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addpersonalinfo";
    }
    @GetMapping("/updateeducation/{id}")
    public String updateEducation(@PathVariable("id") long id, Model model){
        model.addAttribute("roboeducation", educationRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addeducation";
    }
    @GetMapping("/updateexperience/{id}")
    public String updateExperience(@PathVariable("id") long id, Model model){
        model.addAttribute("robowork", workRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addworkexperience";
    }
    @GetMapping("/updateskill/{id}")
    public String updateSkill(@PathVariable("id") long id, Model model){
        model.addAttribute("roboskills", skillsRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addskills";
    }

    @GetMapping("/deleteeducation/{id}")
    public String deleteEducation(@PathVariable("id") long id, Model model){
        educationRepository.delete(id);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "redirect:/test";
    }
    @GetMapping("/deletework/{id}")
    public String deleteWork(@PathVariable("id") long id, Model model){
        workRepository.delete(id);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "redirect:/test";
    }
    @GetMapping("/deleteskill/{id}")
    public String deleteSkill(@PathVariable("id") long id, Model model){
        skillsRepository.delete(id);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "redirect:/test";
    }

    @GetMapping("/login")
    public String logon(Model model) {
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());

        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    @GetMapping("/welcome")
    public String WelcomeAfterLogin(Model model) {
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "welcome2";
    }
}
