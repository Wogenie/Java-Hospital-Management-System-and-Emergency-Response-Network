import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import org.json.*;

class HospitalFinder {

    public void findHospital(JFrame mainFrame, JPanel mainPanel) {
        JLabel infoLabel = new JLabel("Click the button to get the nearest health center around you");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton findButton = new JButton("Find Nearest Hospitals");
        JTextArea locationArea = new JTextArea(2, 50);

        JButton backButton = new JButton("Return to main menu");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(infoLabel, BorderLayout.NORTH);
        topPanel.add(findButton, BorderLayout.SOUTH);

        JPanel hospitalPanel = new JPanel();
        hospitalPanel.setLayout(new BoxLayout(hospitalPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(hospitalPanel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(locationArea, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        findButton.addActionListener(e -> {
            hospitalPanel.removeAll();
            try {
                double[] userLocation = getUserLocation();
                double lat = userLocation[0];
                double lon = userLocation[1];
                String userPlace = getCityName(lat, lon);
                locationArea.setText("Detected Location: " + userPlace);

                String json = fetchNearbyHospitalsFromOSM(lat, lon);
                displayHospitals(json, lat, lon, hospitalPanel);

            } catch (Exception ex) {
                locationArea.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        backButton.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e){

                mainFrame.getContentPane().removeAll();
                mainFrame.add(BorderLayout.SOUTH,mainPanel);
                

                mainFrame.repaint();
                mainFrame.revalidate();

            }
        });

        mainFrame.getContentPane().removeAll();
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, FlowLayout.CENTER);
        mainFrame.add(backButton, BorderLayout.SOUTH);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void displayHospitals(String json, double userLat, double userLon, JPanel hospitalPanel) {
        JSONObject obj = new JSONObject(json);
        JSONArray elements = obj.getJSONArray("elements");

        if (elements.length() == 0) {
            hospitalPanel.add(new JLabel("No hospitals found nearby."));
            return;
        }

        int count = 0;
        for (int i = 0; i < elements.length() && count < 3; i++) {
            JSONObject hospital = elements.getJSONObject(i);
            double hospLat = hospital.getDouble("lat");
            double hospLon = hospital.getDouble("lon");
            double distance = calculateDistance(userLat, userLon, hospLat, hospLon);

            JSONObject tags = hospital.optJSONObject("tags");
            if (tags != null && tags.has("name")) {
                String name = tags.getString("name");
                String contact = "No contact available";
                if (tags.has("contact:phone")) {
                    contact = tags.getString("contact:phone");
                } else if (tags.has("contact:email")) {
                    contact = tags.getString("contact:email");
                }

                JPanel entry = new JPanel(new BorderLayout());
                JLabel hospitalInfo = new JLabel("<html><b>" + (count + 1) + ". " + name +
                        "</b> (" + String.format("%.2f", distance) + " km) - Contact: " + contact + "</html>");

                JButton emailButton = new JButton("Send Emergency Email");
                emailButton.setFont(new Font("Arial", Font.PLAIN, 10));
                emailButton.setMargin(new Insets(2, 4, 2, 4));

                String finalContact = contact;
                String hospitalName = name;
                emailButton.addActionListener(ev -> {
                    try {
                        if (finalContact.contains("@")) {
                            Desktop desktop = Desktop.getDesktop();
                            URI mailto = new URI("mailto:" + finalContact + "?subject=Emergency&body=We need urgent medical help at " + hospitalName);
                            desktop.mail(mailto);
                        } else {
                            JOptionPane.showMessageDialog(null, "No valid email contact.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Failed to open email client.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                entry.add(hospitalInfo, BorderLayout.CENTER);
                entry.add(emailButton, BorderLayout.EAST);

                hospitalPanel.add(entry);
                hospitalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                count++;
            }
        }

        if (count == 0)
            hospitalPanel.add(new JLabel("No named hospitals found nearby."));
    }

    private static double[] getUserLocation() throws IOException, JSONException {
        URL url = new URL("https://ipapi.co/json/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "JavaApp");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) sb.append(line);
        in.close();

        JSONObject obj = new JSONObject(sb.toString());
        double lat = obj.getDouble("latitude");
        double lon = obj.getDouble("longitude");
        return new double[]{lat, lon};
    }

    private static String getCityName(double lat, double lon) throws IOException, JSONException {
        String urlStr = String.format("https://nominatim.openstreetmap.org/reverse?lat=%f&lon=%f&format=json", lat, lon);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "JavaApp");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) sb.append(line);
        in.close();

        JSONObject obj = new JSONObject(sb.toString());
        JSONObject address = obj.getJSONObject("address");
        if (address.has("city")) {
            return address.getString("city");
        } else if (address.has("town")) {
            return address.getString("town");
        } else if (address.has("village")) {
            return address.getString("village");
        }
        return "Unknown location";
    }

    private static String fetchNearbyHospitalsFromOSM(double lat, double lon) throws IOException {
        String query = "[out:json];node[amenity=hospital](around:5000," + lat + "," + lon + ");out;";
        URL url = new URL("https://overpass-api.de/api/interpreter");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.write(("data=" + URLEncoder.encode(query, "UTF-8")).getBytes());
        os.flush();
        os.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        return sb.toString();
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}