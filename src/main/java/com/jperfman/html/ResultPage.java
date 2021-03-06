package com.jperfman.html;

// Autogenerated Jamon proxy
// /opt/kai.zhang/jamon/./ResultPage.jamon

// 2, 2
import com.jperfman.result.ResultAnalysis;
// 3, 2
import java.io.BufferedReader;
// 4, 2
import java.io.File;
// 5, 2
import java.io.FileInputStream;
// 6, 2
import java.io.FileNotFoundException;
// 7, 2
import java.io.IOException;
// 8, 2
import java.io.InputStreamReader;
// 9, 2
import java.util.List;
// 10, 2
import java.util.Map;

@org.jamon.annotations.Template(
  signature = "F261795C1FADF82AEC88C8ABCCDAABB2",
  requiredArguments = {
    @org.jamon.annotations.Argument(name = "resultAnalysis", type = "ResultAnalysis"),
    @org.jamon.annotations.Argument(name = "resultDir", type = "String")})
public class ResultPage
  extends org.jamon.AbstractTemplateProxy
{
  
  public ResultPage(org.jamon.TemplateManager p_manager)
  {
     super(p_manager);
  }
  
  public ResultPage()
  {
     super("/ResultPage");
  }
  
  protected interface Intf
    extends org.jamon.AbstractTemplateProxy.Intf{
    
    void renderNoFlush(final java.io.Writer jamonWriter) throws java.io.IOException;
    
  }
  public static class ImplData
    extends org.jamon.AbstractTemplateProxy.ImplData
  {
    // 14, 2
    public void setResultAnalysis(ResultAnalysis resultAnalysis)
    {
      // 14, 2
      m_resultAnalysis = resultAnalysis;
    }
    public ResultAnalysis getResultAnalysis()
    {
      return m_resultAnalysis;
    }
    private ResultAnalysis m_resultAnalysis;
    // 15, 2
    public void setResultDir(String resultDir)
    {
      // 15, 2
      m_resultDir = resultDir;
    }
    public String getResultDir()
    {
      return m_resultDir;
    }
    private String m_resultDir;
  }
  @Override
  protected ImplData makeImplData()
  {
    return new ImplData();
  }
  @Override @SuppressWarnings("unchecked") public ImplData getImplData()
  {
    return (ImplData) super.getImplData();
  }
  
  
  @Override
  public org.jamon.AbstractTemplateImpl constructImpl(Class<? extends org.jamon.AbstractTemplateImpl> p_class){
    try
    {
      return p_class
        .getConstructor(new Class [] { org.jamon.TemplateManager.class, ImplData.class })
        .newInstance(new Object [] { getTemplateManager(), getImplData()});
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  protected org.jamon.AbstractTemplateImpl constructImpl(){
    return new ResultPageImpl(getTemplateManager(), getImplData());
  }
  public org.jamon.Renderer makeRenderer(final ResultAnalysis resultAnalysis, final String resultDir)
  {
    return new org.jamon.AbstractRenderer() {
      @Override
      public void renderTo(final java.io.Writer jamonWriter)
        throws java.io.IOException
      {
        render(jamonWriter, resultAnalysis, resultDir);
      }
    };
  }
  
  public void render(final java.io.Writer jamonWriter, final ResultAnalysis resultAnalysis, final String resultDir)
    throws java.io.IOException
  {
    renderNoFlush(jamonWriter, resultAnalysis, resultDir);
    jamonWriter.flush();
  }
  public void renderNoFlush(final java.io.Writer jamonWriter, final ResultAnalysis resultAnalysis, final String resultDir)
    throws java.io.IOException
  {
    ImplData implData = getImplData();
    implData.setResultAnalysis(resultAnalysis);
    implData.setResultDir(resultDir);
    Intf instance = (Intf) getTemplateManager().constructImpl(this);
    instance.renderNoFlush(jamonWriter);
    reset();
  }
  
  
}
