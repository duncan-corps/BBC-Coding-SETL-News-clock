package uk.org.fwei;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Simple Web UI {@link Controller} so that users can view and affect
 * {@link LeftTabService}'s internal state. Leverages Thymeleaf (see
 * /src/main/resources/templates/view.html); though there's other, newer, and
 * better, Web UI frameworks, Thymeleaf is/was Spring Boot's default.
 * 
 * @author dpc
 */
@Controller
@RequestMapping("/")
public class ViewController {

	private final LeftTabService leftTabService;

	public ViewController(final LeftTabService leftTabService) {
		this.leftTabService = leftTabService;
	}

	/**
	 * Utility method which exposes {@link LeftTabService}'s internal state, via
	 * {@link LeftTabModel}, to the Web UI and sends the user to the specified view
	 * to render it.
	 * 
	 * @param viewName
	 * @return
	 */
	private final ModelAndView modelAndView(final String viewName) {
		final ModelAndView modelAndView = new ModelAndView(viewName, "leftTab", leftTabService.model());

		return modelAndView;
	}

	/**
	 * Default view.
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView view() {
		return modelAndView("view");
	}

	/**
	 * Calls {@link LeftTabService#state(String)} with the new value supplied in the
	 * path.
	 * 
	 * @param newState
	 * @return
	 */
	// Should be Put, really
	@GetMapping("leftTab/{state}")
	public ModelAndView putLeftTabState(@PathVariable("state") final String newState) {
		// TODO DPC:DPC Could move most of this logic into LeftTabService#state(String)
		if ("on".equals(newState) || "off".equals(newState)) {
			final String oldState = leftTabService.state(newState);

			if (!oldState.equals(newState)) {
				leftTabService.invoke();
			}
		}

		return modelAndView("view");
	}
}
