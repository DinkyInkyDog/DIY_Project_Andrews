/**
 * 
 */
package projects.entity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Promineo
 *
 */
public class Project {
  private Integer project_id;
  private String project_name;
  private BigDecimal estimated_hours;
  private BigDecimal actual_hours;
  private Integer difficulty;
  private String notes;

  private List<Material> materials = new LinkedList<>();
  private List<Step> steps = new LinkedList<>();
  private List<Category> categories = new LinkedList<>();

  public Integer getProjectId() {
    return project_id;
  }

  public void setProjectId(Integer projectId) {
    this.project_id = projectId;
  }

  public String getProjectName() {
    return project_name;
  }

  public void setProjectName(String projectName) {
    this.project_name = projectName;
  }

  public BigDecimal getEstimatedHours() {
    return estimated_hours;
  }

  public void setEstimatedHours(BigDecimal estimatedHours) {
    this.estimated_hours = estimatedHours;
  }

  public BigDecimal getActualHours() {
    return actual_hours;
  }

  public void setActualHours(BigDecimal actualHours) {
    this.actual_hours = actualHours;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<Material> getMaterials() {
    return materials;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public List<Category> getCategories() {
    return categories;
  }

  @Override
  public String toString() {
    String result = "";
    
    result += "\n   ID=" + project_id;
    result += "\n   name=" + project_name;
    result += "\n   estimatedHours=" + estimated_hours;
    result += "\n   actualHours=" + actual_hours;
    result += "\n   difficulty=" + difficulty;
    result += "\n   notes=" + notes;
    
    result += "\n   Materials:";
    
    for(Material material : materials) {
      result += "\n      " + material;
    }
    
    result += "\n   Steps:";
    
    for(Step step : steps) {
      result += "\n      " + step;
    }
    
    result += "\n   Categories:";
    
    for(Category category : categories) {
      result += "\n      " + category;
    }
    
    return result;
  }
}
