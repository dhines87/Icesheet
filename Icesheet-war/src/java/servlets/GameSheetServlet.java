/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.itextpdf.io.font.constants.StandardFonts;
import dtos.GameEJBDTO;
import ejbs.GamesFacadeBean;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import dtos.AssistEJBDTO;
import dtos.GoalEJBDTO;
import dtos.PenaltyEJBDTO;
import dtos.PlayerEJBDTO;
import helpers.FontOptions;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author David
 */
@WebServlet(name = "GameSheetServlet", urlPatterns = {"/icesheet/gamesheet"})
public class GameSheetServlet extends HttpServlet {
    @EJB
    private GamesFacadeBean gamesFacadeBean;
    private final DateFormat dateFormat = new SimpleDateFormat("MMM - DD - yyyy");
    private final float[] columnWidths = { 5, 45, 5, 45 };
    private final FontOptions defaultCellFontOptions;
    private final FontOptions cellHeaderFontOptions;
    private final FontOptions cellSubHeaderFontOptions;

    public GameSheetServlet() throws IOException {
        this.defaultCellFontOptions = new FontOptions(StandardFonts.TIMES_ROMAN, ColorConstants.BLACK, 10, TextAlignment.LEFT);
        this.cellHeaderFontOptions = new FontOptions(StandardFonts.TIMES_BOLD, ColorConstants.BLACK, 14, TextAlignment.CENTER);
        this.cellSubHeaderFontOptions = new FontOptions(StandardFonts.TIMES_ROMAN, ColorConstants.BLACK, 10, TextAlignment.CENTER);        
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        int gameid = Integer.parseInt(request.getParameter("game"));
        GameEJBDTO game = this.gamesFacadeBean.readGameById(gameid);
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
            Document doc = new Document(pdfDoc);  
            
            doc.add(new Paragraph(new Text(String.format("Gamesheet")).setBold())
                    .setFontSize(42)
                    .setTextAlignment(TextAlignment.CENTER)
            );
            
            doc.add(new Paragraph(String.format("Date: " + this.dateFormat.format(new Date(game.getGameDate())))));
            
            Table teamsTable = new Table(UnitValue.createPercentArray(this.columnWidths)).useAllAvailableWidth();     
            
            teamsTable.addCell(new Cell(1, 2)
                    .add(new Paragraph()
                            .add(new Text("HOME TEAM: ").setBold())
                            .add(new Text(game.getHomeTeam().getName()))
                            .add(new Text(" - " + game.getHomeTeamScore()))
                    )
            );
            
            teamsTable.addCell(new Cell(1, 2)
                    .add(new Paragraph()
                            .add(new Text("VISITOR TEAM: ").setBold())
                            .add(new Text(game.getVisitorTeam().getName()))                            
                            .add(new Text(" - " + game.getVisitorTeamScore()))
                    )                            
                );
            
            teamsTable.addCell(this.createCell("NO", 1, 1, cellSubHeaderFontOptions));
            teamsTable.addCell(this.createCell("NAME", 1, 1, cellSubHeaderFontOptions));
            teamsTable.addCell(this.createCell("NO", 1, 1, cellSubHeaderFontOptions));
            teamsTable.addCell(this.createCell("NAME", 1, 1, cellSubHeaderFontOptions));

            boolean allPlayersAdded = false;
            int playerIndex = 0;
            int numHomePlayers = game.getHomeTeam().getPlayers().size();            
            int numVisitorPlayers = game.getVisitorTeam().getPlayers().size();
            
            while (!allPlayersAdded) {
                teamsTable.addCell(this.createCell(playerIndex < numHomePlayers ? game.getHomeTeam().getPlayers().get(playerIndex).getNumber().toString() : "", 1, 1, defaultCellFontOptions));
                teamsTable.addCell(this.createCell(playerIndex < numHomePlayers ? this.formatPlayerName(game.getHomeTeam().getPlayers().get(playerIndex)) : "", 1, 1, defaultCellFontOptions));
                teamsTable.addCell(this.createCell(playerIndex < numVisitorPlayers ? game.getVisitorTeam().getPlayers().get(playerIndex).getNumber().toString() : "", 1, 1, defaultCellFontOptions));
                teamsTable.addCell(this.createCell(playerIndex < numVisitorPlayers ? this.formatPlayerName(game.getVisitorTeam().getPlayers().get(playerIndex)) : "", 1, 1, defaultCellFontOptions));
                playerIndex++;
                if (playerIndex >= numHomePlayers && playerIndex >= numVisitorPlayers) {
                    allPlayersAdded = true;
                }
            }   
            
            doc.add(teamsTable);
            
            doc.add(new Paragraph(new Text(String.format("Scoring Summary")).setBold())
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
            );
            
            for (GoalEJBDTO goal : game.getGoals()) {
                
                String goalLine = String.format("%s - goal scored by: # %s %s %s", 
                        getPlayerTeamName(goal.getPlayer(), game), 
                        goal.getPlayer().getNumber().toString(), 
                        goal.getPlayer().getFirstname(), 
                        goal.getPlayer().getLastname());
                
                List<AssistEJBDTO> assists = game
                        .getAssists()
                        .stream()
                        .filter(assist -> assist.getGoalid().equals(goal.getGoalid()))
                        .collect(Collectors.toList());
                
                if (assists != null && assists.size() > 0) {
                    goalLine += " assisted by: ";
                    
                    for (int i = 0; i < assists.size(); i++) {
                        
                        goalLine += i > 0 ? ", and " : "";
                        
                        goalLine += String.format("# %s %s %s", 
                                assists.get(i).getPlayer().getNumber().toString(), 
                                assists.get(i).getPlayer().getFirstname(), 
                                assists.get(i).getPlayer().getLastname()
                        );
                    }
                }
                
                goalLine += String.format(" (%s, %s)", goal.getTime(), ordinalSuffix(goal.getPeriod()));
                
                doc.add(new Paragraph(new Text(goalLine))
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT)
                );
            }
            
            doc.add(new Paragraph(new Text(String.format("Penalty Summary")).setBold())
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
            );
            
            for (PenaltyEJBDTO penalty : game.getPenalties()) {
                
                String penaltyLine = String.format("%s - penalty: # %s %s %s %s minutes for %s (%s, %s)", 
                    getPlayerTeamName(penalty.getPlayer(), game), 
                    penalty.getPlayer().getNumber().toString(), 
                    penalty.getPlayer().getFirstname(), 
                    penalty.getPlayer().getLastname(),
                    penalty.getMinutes(),
                    penalty.getPenalty(),
                    penalty.getTime(),
                    ordinalSuffix(penalty.getPeriod())
                );
                
                doc.add(new Paragraph(penaltyLine)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT)
                );
                
                
            }
            
            doc.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        }
        catch(Exception ex) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "severe - {0}", ex.getMessage());
        }
        
    }
    
    private Cell createCell(String content, int colspan, int rowspan, FontOptions fontOptions) {
        return new Cell(rowspan, colspan)
                .add(new Paragraph(String.format(content))
                    .setFont(fontOptions.getFont())
                    .setFontSize(fontOptions.getFontSize())
                    .setFontColor(fontOptions.getFontColor()))
                .setTextAlignment(fontOptions.getTextAlignment());
        
    }
    
    private String formatPlayerName(PlayerEJBDTO player) {
        return player.getFirstname() + " " + player.getLastname();
    }
    
    private String getPlayerTeamName(PlayerEJBDTO player, GameEJBDTO game) {
        return Objects.equals(game.getHomeTeam().getTeamId(), player.getTeamid()) ? game.getHomeTeam().getName() : game.getVisitorTeam().getName();
    }
    
    private String ordinalSuffix(int i) {
        int j = i % 10,
            k = i % 100;
        if (j == 1 && k != 11) {
            return i + "st";
        }
        if (j == 2 && k != 12) {
            return i + "nd";
        }
        if (j == 3 && k != 13) {
            return i + "rd";
        }
        return i + "th";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}